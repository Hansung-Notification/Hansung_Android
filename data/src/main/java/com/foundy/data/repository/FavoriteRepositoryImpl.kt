package com.foundy.data.repository

import com.foundy.data.repository.favorite.FavoriteLocalDataSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    override fun getAll(): Result<List<Notice>> {
        return try {
            Result.success(favoriteLocalDataSource.getAll())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}