package com.foundy.data.source.query

import com.foundy.data.converter.toEntity
import com.foundy.data.converter.toModel
import com.foundy.data.db.QueryDao
import com.foundy.domain.model.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QueryLocalDataSourceImpl @Inject constructor(
    private val queryDao: QueryDao
) : QueryLocalDataSource {

    override fun getRecentList(): Flow<List<Query>> {
        return queryDao.getRecentList().map { list -> list.map { it.toModel() } }
    }

    override suspend fun addRecent(query: Query) {
        queryDao.insert(query.toEntity())
    }

    override suspend fun removeRecent(query: Query) {
        queryDao.delete(query.toEntity())
    }

    override suspend fun updateRecent(query: Query) {
        queryDao.update(query.toEntity())
    }
}