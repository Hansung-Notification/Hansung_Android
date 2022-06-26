package com.foundy.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.foundy.data.api.NoticeApi
import com.foundy.data.paging.NoticePagingSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
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
}