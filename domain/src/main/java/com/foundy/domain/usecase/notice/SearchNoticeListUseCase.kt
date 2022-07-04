package com.foundy.domain.usecase.notice

import com.foundy.domain.repository.NoticeRepository
import javax.inject.Inject

class SearchNoticeListUseCase @Inject constructor(
    private val repository: NoticeRepository
) {
    operator fun invoke(query: String) = repository.searchNoticeList(query)
}