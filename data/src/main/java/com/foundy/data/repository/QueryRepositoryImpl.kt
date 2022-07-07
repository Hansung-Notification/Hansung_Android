package com.foundy.data.repository

import com.foundy.data.source.query.QueryLocalDataSource
import com.foundy.domain.model.Query
import com.foundy.domain.repository.QueryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QueryRepositoryImpl @Inject constructor(
    private val queryLocalDataSource: QueryLocalDataSource
) : QueryRepository {

    override fun getRecentList(): Flow<List<Query>> {
        return queryLocalDataSource.getRecentList()
    }

    override suspend fun addRecent(query: Query) {
        queryLocalDataSource.addRecent(query)
    }

    override suspend fun removeRecent(query: Query) {
        queryLocalDataSource.removeRecent(query)
    }

    override suspend fun updateRecent(query: Query) {
        queryLocalDataSource.updateRecent(query)
    }
}