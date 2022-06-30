package com.foundy.data.source.favorite

import com.foundy.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface FavoriteLocalDataSource {
    fun getAll(): Flow<List<Notice>>
    suspend fun add(notice: Notice)
    suspend fun remove(notice: Notice)
}