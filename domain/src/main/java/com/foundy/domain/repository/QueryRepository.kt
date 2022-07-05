package com.foundy.domain.repository

import com.foundy.domain.model.Query
import kotlinx.coroutines.flow.Flow

interface QueryRepository {
    fun getRecentList(): Flow<List<Query>>
    suspend fun addRecent(query: Query)
    suspend fun removeRecent(query: Query)
    suspend fun updateRecent(query: Query)
}