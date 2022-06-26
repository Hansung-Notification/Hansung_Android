package com.foundy.data.source.favorite

import com.foundy.data.db.FavoriteDao
import com.foundy.domain.model.Notice
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoriteLocalDataSource {

    override suspend fun getAll(): List<Notice> {
        return favoriteDao.getAll()
    }

    override suspend fun add(notice: Notice) {
        favoriteDao.insert(notice)
    }

    override suspend fun remove(notice: Notice) {
        favoriteDao.delete(notice)
    }
}