package com.foundy.domain.usecase.notice

import com.foundy.domain.repository.NoticeRepository
import javax.inject.Inject

class HasSearchResultUseCase @Inject constructor(
    private val repository: NoticeRepository
) {
    suspend operator fun invoke(query: String) = repository.hasSearchResult(query)
}