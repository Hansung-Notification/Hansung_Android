package com.foundy.domain.usecase.keyword

import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import javax.inject.Inject

class RemoveKeywordUseCase @Inject constructor(
    private val repository: KeywordRepository
) {
    suspend operator fun invoke(keyword: Keyword) = repository.remove(keyword)
}