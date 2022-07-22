package com.foundy.data.repository

import com.foundy.data.source.favorite.FavoriteLocalDataSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteLocalDataSource: FavoriteLocalDataSource
) : FavoriteRepository {

    private val favorites by lazy {
        favoriteLocalDataSource.getAll().stateIn(
            MainScope(),
            SharingStarted.Eagerly,
            emptyList()
        )
    }

    override fun getAll(): Flow<List<Notice>> = favorites

    override fun isFavorite(notice: Notice) : Boolean {
        return favorites.value.any { it.url == notice.url }
    }

    override suspend fun add(notice: Notice) {
        favoriteLocalDataSource.add(notice)
    }

    override suspend fun remove(notice: Notice) {
        favoriteLocalDataSource.remove(notice)
    }
}