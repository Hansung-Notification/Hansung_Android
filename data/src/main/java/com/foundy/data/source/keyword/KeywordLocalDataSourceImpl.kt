package com.foundy.data.source.keyword

import com.foundy.data.db.KeywordDao
import com.foundy.domain.model.Keyword
import javax.inject.Inject

class KeywordLocalDataSourceImpl @Inject constructor(
    private val keywordDao: KeywordDao
): KeywordLocalDataSource {

    override suspend fun getAll(): List<Keyword> {
        return keywordDao.getAll()
    }

    override suspend fun add(keyword: Keyword) {
        keywordDao.insert(keyword)
    }

    override suspend fun remove(keyword: Keyword) {
        keywordDao.delete(keyword)
    }
}