package com.foundy.data.repository

import com.foundy.data.source.keyword.KeywordLocalDataSource
import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val keywordLocalDataSource: KeywordLocalDataSource
) : KeywordRepository {

    override fun getAll(): Flow<List<Keyword>> {
        return keywordLocalDataSource.getAll()
    }

    override suspend fun add(keyword: Keyword) {
        withContext(Dispatchers.IO) {
            keywordLocalDataSource.add(keyword)
        }
    }

    override suspend fun remove(keyword: Keyword) {
        withContext(Dispatchers.IO) {
            keywordLocalDataSource.remove(keyword)
        }
    }
}