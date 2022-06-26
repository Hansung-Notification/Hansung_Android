package com.foundy.domain.usecase

import androidx.paging.PagingData
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    operator fun invoke(): Flow<PagingData<Notice>> = noticeRepository.getNoticeList()
}