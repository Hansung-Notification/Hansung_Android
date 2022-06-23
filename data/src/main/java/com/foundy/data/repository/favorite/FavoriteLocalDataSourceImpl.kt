package com.foundy.data.repository.favorite

import com.foundy.data.db.FavoriteDao
import com.foundy.domain.model.Notice
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoriteLocalDataSource {

    override fun getAll(): List<Notice> {
        return favoriteDao.getAll()
    }
}