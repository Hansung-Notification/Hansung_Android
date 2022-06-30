package com.foundy.domain.repository

import com.foundy.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface KeywordRepository {
    fun getAll(): Flow<List<Keyword>>
    suspend fun add(keyword: Keyword)
    suspend fun remove(keyword: Keyword)
}