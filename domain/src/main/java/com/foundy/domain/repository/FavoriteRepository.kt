package com.foundy.domain.repository

import com.foundy.domain.model.Notice

interface FavoriteRepository {
    fun getAll(): Result<List<Notice>>
}