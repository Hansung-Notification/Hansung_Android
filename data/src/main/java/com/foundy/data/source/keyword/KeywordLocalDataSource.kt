package com.foundy.data.source.keyword

import com.foundy.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface KeywordLocalDataSource {
    fun getAll(): Flow<List<Keyword>>
    suspend fun add(keyword: Keyword)
    suspend fun remove(keyword: Keyword)
}