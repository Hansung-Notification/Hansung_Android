package com.foundy.data.repository

import com.foundy.data.source.favorite.FavoriteLocalDataSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override fun getAll(): Flow<List<Notice>> {
        return favoriteLocalDataSource.getAll()
    }

    override suspend fun add(notice: Notice) {
        withContext(Dispatchers.IO) {
            favoriteLocalDataSource.add(notice)
        }
    }

    override suspend fun remove(notice: Notice) {
        withContext(Dispatchers.IO) {
            favoriteLocalDataSource.remove(notice)
        }
    }
}