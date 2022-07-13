package com.foundy.hansungnotification.fake

import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeFavoriteRepositoryImpl: FavoriteRepository {

    private val stateFlow = MutableStateFlow<List<Notice>>(emptyList())

    suspend fun emit(notices: List<Notice>) {
        stateFlow.emit(notices)
    }

    override fun getAll(): Flow<List<Notice>> = stateFlow

    override fun isFavorite(notice: Notice): Boolean {
        return stateFlow.value.any { it.url == notice.url }
    }

    override suspend fun add(notice: Notice) {
        val newList = mutableListOf(*stateFlow.value.toTypedArray())
        newList.add(notice)
        emit(newList)
    }

    override suspend fun remove(notice: Notice) {
        val newList = mutableListOf(*stateFlow.value.toTypedArray())
        newList.remove(notice)
        emit(newList)
    }
}