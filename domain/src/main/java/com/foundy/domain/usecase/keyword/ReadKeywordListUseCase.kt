package com.foundy.domain.usecase.keyword

import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadKeywordListUseCase @Inject constructor(
    private val repository: KeywordRepository
) {
    operator fun invoke(): Flow<List<Keyword>> = repository.getAll()
}