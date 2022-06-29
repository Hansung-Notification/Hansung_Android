package com.foundy.domain.repository

import com.foundy.domain.model.Keyword

interface KeywordRepository {
    suspend fun getAll(): Result<List<Keyword>>
    suspend fun add(keyword: Keyword)
    suspend fun remove(keyword: Keyword)
}