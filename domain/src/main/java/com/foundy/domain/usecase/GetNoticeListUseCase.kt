package com.foundy.domain.usecase

import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import javax.inject.Inject

class GetNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke(): Result<List<Notice>> = noticeRepository.getNoticeList()
}