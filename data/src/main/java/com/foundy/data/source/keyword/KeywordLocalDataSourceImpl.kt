package com.foundy.data.source.keyword

import com.foundy.data.converter.toDomain
import com.foundy.data.converter.toEntity
import com.foundy.data.db.KeywordDao
import com.foundy.domain.model.Keyword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KeywordLocalDataSourceImpl @Inject constructor(
    private val keywordDao: KeywordDao
): KeywordLocalDataSource {

    override fun getAll(): Flow<List<Keyword>> {
        return keywordDao.getAll().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun add(keyword: Keyword) {
        keywordDao.insert(keyword.toEntity())
    }

    override suspend fun remove(keyword: Keyword) {
        keywordDao.delete(keyword.toEntity())
    }
}