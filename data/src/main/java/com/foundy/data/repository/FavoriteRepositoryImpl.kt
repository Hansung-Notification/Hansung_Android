package com.foundy.data.repository

import com.foundy.data.repository.favorite.FavoriteLocalDataSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override suspend fun getAll(): Result<List<Notice>> {
        return try {
            val list = withContext(Dispatchers.IO) {
                favoriteLocalDataSource.getAll()
            }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
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