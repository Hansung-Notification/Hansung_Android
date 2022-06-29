package com.foundy.domain.usecase.keyword

import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import javax.inject.Inject

class ReadKeywordListUseCase @Inject constructor(
    private val repository: KeywordRepository
) {
    suspend operator fun invoke(): Result<List<Keyword>> = repository.getAll()
}