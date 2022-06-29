package com.foundy.data.repository

import com.foundy.data.source.keyword.KeywordLocalDataSource
import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val keywordLocalDataSource: KeywordLocalDataSource
) : KeywordRepository {

    override suspend fun getAll(): Result<List<Keyword>> {
        return try {
            val list = withContext(Dispatchers.IO) {
                keywordLocalDataSource.getAll()
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
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