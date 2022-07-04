package com.foundy.domain.repository

import androidx.paging.PagingData
import com.foundy.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    fun getNoticeList(): Flow<PagingData<Notice>>
    fun searchNoticeList(query: String): Flow<PagingData<Notice>>
}