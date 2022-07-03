package com.foundy.domain.repository

import com.foundy.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface KeywordRepository {
    fun getAll(): Flow<Result<List<Keyword>>>
    fun add(keyword: Keyword)
    fun remove(keyword: Keyword)
}