package com.foundy.domain.repository

import com.foundy.domain.model.Notice

interface FavoriteRepository {
    suspend fun getAll(): Result<List<Notice>>
    suspend fun add(notice: Notice)
    suspend fun remove(notice: Notice)
}