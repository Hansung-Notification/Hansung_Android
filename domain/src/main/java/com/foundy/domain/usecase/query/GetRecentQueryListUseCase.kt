package com.foundy.domain.usecase.query

import com.foundy.domain.model.Query
import com.foundy.domain.repository.QueryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecentQueryListUseCase @Inject constructor(
    private val repository: QueryRepository
) {
    operator fun invoke(): Flow<List<Query>> = repository.getRecentList()
}