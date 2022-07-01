package com.foundy.hansungnotification.fake

import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeFavoriteRepositoryImpl: FavoriteRepository {

    private val sharedFlow = MutableSharedFlow<List<Notice>>()
    private val noticeList = mutableListOf<Notice>()

    fun setFakeList(notices: List<Notice>) {
        noticeList.clear()
        noticeList.addAll(notices)
    }

    suspend fun emitFake() {
        sharedFlow.emit(noticeList)
    }

    override fun getAll(): Flow<List<Notice>> = sharedFlow

    override suspend fun add(notice: Notice) {
        noticeList.add(notice)
        sharedFlow.emit(noticeList)
    }

    override suspend fun remove(notice: Notice) {
        noticeList.remove(notice)
        sharedFlow.emit(noticeList)
    }
}