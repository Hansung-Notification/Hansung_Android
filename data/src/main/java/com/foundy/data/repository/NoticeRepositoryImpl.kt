package com.foundy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.foundy.data.api.NoticeApi
import com.foundy.data.constant.WebConstant
import com.foundy.data.constant.WebConstant.START_PAGE
import com.foundy.data.mapper.NoticeMapper
import com.foundy.data.source.notice.NoticePagingSource
import com.foundy.data.source.notice.SearchingNoticePagingSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val noticeApi: NoticeApi,
) : NoticeRepository {

    companion object {
        const val NOTICE_PAGE_SIZE = 30
    }

    override fun getNoticeList(): Flow<PagingData<Notice>> {
        return Pager(
            config = PagingConfig(pageSize = NOTICE_PAGE_SIZE),
            pagingSourceFactory = { NoticePagingSource(noticeApi) }
        ).flow
    }

    override fun searchNoticeList(query: String): Flow<PagingData<Notice>> {
        return Pager(
            config = PagingConfig(pageSize = NOTICE_PAGE_SIZE),
            pagingSourceFactory = { SearchingNoticePagingSource(noticeApi, query = query) }
        ).flow
    }

    override suspend fun hasSearchResult(query: String): Result<Boolean> {
        return try {
            val response = noticeApi.searchNoticeList(
                page = START_PAGE,
                param = WebConstant.createSearchPostParameter(query)
            )
            if (response.isSuccessful) {
                val notices = NoticeMapper(response.body()!!).filter { !it.isHeader }
                Result.success(notices.isNotEmpty())
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}