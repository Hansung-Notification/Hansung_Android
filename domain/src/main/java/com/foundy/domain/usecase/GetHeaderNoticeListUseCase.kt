package com.foundy.domain.usecase

import com.foundy.domain.repository.NoticeRepository
import javax.inject.Inject

class GetHeaderNoticeListUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    suspend operator fun invoke() = noticeRepository.getHeaderNoticeList()
}