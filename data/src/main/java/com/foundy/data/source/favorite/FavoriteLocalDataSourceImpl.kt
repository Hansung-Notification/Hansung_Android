package com.foundy.data.source.favorite

import com.foundy.data.converter.toFavoriteNoticeEntity
import com.foundy.data.converter.toNotice
import com.foundy.data.db.FavoriteDao
import com.foundy.domain.model.Notice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
): FavoriteLocalDataSource {

    override fun getAll(): Flow<List<Notice>> {
        return favoriteDao.getAll().map { list -> list.map { it.toNotice() }}
    }

    override suspend fun add(notice: Notice) {
        favoriteDao.insert(notice.toFavoriteNoticeEntity())
    }

    override suspend fun remove(notice: Notice) {
        favoriteDao.delete(notice.toFavoriteNoticeEntity())
    }
}