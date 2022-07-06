package com.foundy.hansungnotification.fake

import com.foundy.domain.model.Query
import com.foundy.domain.repository.QueryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeQueryRepositoryImpl: QueryRepository {

    private val sharedFlow = MutableSharedFlow<List<Query>>()
    private val queryList = mutableListOf<Query>()

    override fun getRecentList(): Flow<List<Query>> = sharedFlow

    private suspend fun emit() {
        sharedFlow.emit(queryList)
    }

    override suspend fun addRecent(query: Query) {
        queryList.add(query)
        emit()
    }

    override suspend fun removeRecent(query: Query) {
        queryList.remove(query)
        emit()
    }

    override suspend fun updateRecent(query: Query) {
        for (i in queryList.indices) {
            if (queryList[i].content == query.content) {
                queryList[i] = query
                break
            }
        }
        emit()
    }
}