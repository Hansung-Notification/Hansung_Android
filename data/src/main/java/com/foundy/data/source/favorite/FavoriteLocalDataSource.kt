package com.foundy.data.source.favorite

import com.foundy.domain.model.Notice

interface FavoriteLocalDataSource {
    suspend fun getAll(): List<Notice>
    suspend fun add(notice: Notice)
    suspend fun remove(notice: Notice)
}