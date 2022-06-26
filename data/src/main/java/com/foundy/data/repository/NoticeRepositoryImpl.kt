package com.foundy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.foundy.data.api.NoticeApi
import com.foundy.data.mapper.NoticeMapper
import com.foundy.data.paging.NoticePagingSource
import com.foundy.data.repository.notice.NoticeRemoteDataSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class NoticeRepositoryImpl @Inject constructor(
    private val noticeApi: NoticeApi,
    private val noticeRemoteDataSource: NoticeRemoteDataSource
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

    override suspend fun getHeaderNoticeList(): Result<List<Notice>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                noticeRemoteDataSource.getNoticeList(NoticePagingSource.START_PAGE)
            }
            if (response.isSuccessful) {
                val body = response.body()!!
                val headerNotices = NoticeMapper.Header(body)
                Result.success(headerNotices)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}