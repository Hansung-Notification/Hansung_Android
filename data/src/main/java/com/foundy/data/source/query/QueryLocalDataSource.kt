package com.foundy.data.source.query

import com.foundy.domain.model.Query
import kotlinx.coroutines.flow.Flow

interface QueryLocalDataSource {
    fun getRecentList(): Flow<List<Query>>
    suspend fun addRecent(query: Query)
    suspend fun removeRecent(query: Query)
    suspend fun updateRecent(query: Query)
}