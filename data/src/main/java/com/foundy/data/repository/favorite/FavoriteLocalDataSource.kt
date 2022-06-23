package com.foundy.data.repository.favorite

import com.foundy.domain.model.Notice

interface FavoriteLocalDataSource {
    fun getAll(): List<Notice>
}