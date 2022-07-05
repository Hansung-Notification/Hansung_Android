package com.foundy.domain.usecase.query

import com.foundy.domain.model.Query
import com.foundy.domain.repository.QueryRepository
import javax.inject.Inject

class AddRecentQueryUseCase @Inject constructor(
    private val repository: QueryRepository
) {
    suspend operator fun invoke(query: Query) = repository.addRecent(query)
}