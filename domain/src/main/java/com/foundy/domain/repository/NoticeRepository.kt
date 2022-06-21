package com.foundy.domain.repository

import com.foundy.domain.model.Notice

interface NoticeRepository {
    suspend fun getNoticeList(): Result<List<Notice>>
}