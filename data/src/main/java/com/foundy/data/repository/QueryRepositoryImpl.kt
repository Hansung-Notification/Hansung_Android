package com.foundy.data.repository

import com.foundy.data.source.query.QueryLocalDataSource
import com.foundy.domain.model.Query
import com.foundy.domain.repository.QueryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QueryRepositoryImpl @Inject constructor(
    private val queryLocalDataSource: QueryLocalDataSource
): QueryRepository {

    override fun getRecentList(): Flow<List<Query>> {
        return queryLocalDataSource.getRecentList()
    }

    override suspend fun addRecent(query: Query) {
        withContext(Dispatchers.IO) {
            queryLocalDataSource.addRecent(query)
        }
    }

    override suspend fun removeRecent(query: Query) {
        withContext(Dispatchers.IO) {
            queryLocalDataSource.removeRecent(query)
        }
    }

    override suspend fun updateRecent(query: Query) {
        withContext(Dispatchers.IO) {
            queryLocalDataSource.updateRecent(query)
        }
    }
}