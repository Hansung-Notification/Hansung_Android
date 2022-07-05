package com.foundy.domain.usecase.query

import com.foundy.domain.model.Query
import com.foundy.domain.repository.QueryRepository
import javax.inject.Inject

class RemoveRecentQueryUseCase @Inject constructor(
    private val repository: QueryRepository
) {
    suspend operator fun invoke(query: Query) = repository.removeRecent(query)
}