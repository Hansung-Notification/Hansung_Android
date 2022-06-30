package com.foundy.domain.repository

import com.foundy.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAll(): Flow<List<Notice>>
    suspend fun add(notice: Notice)
    suspend fun remove(notice: Notice)
}