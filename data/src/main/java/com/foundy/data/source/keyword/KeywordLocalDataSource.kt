package com.foundy.data.source.keyword

import com.foundy.domain.model.Keyword

interface KeywordLocalDataSource {
    suspend fun getAll(): List<Keyword>
    suspend fun add(keyword: Keyword)
    suspend fun remove(keyword: Keyword)
}