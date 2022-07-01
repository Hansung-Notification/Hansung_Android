package com.foundy.hansungnotification.fake

import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeKeywordRepositoryImpl : KeywordRepository {

    private val sharedFlow = MutableSharedFlow<List<Keyword>>()
    private val keywordList = mutableListOf<Keyword>()

    fun setFakeList(keywords: List<Keyword>) {
        keywordList.addAll(keywords)
    }

    suspend fun emitFake() {
        sharedFlow.emit(keywordList)
    }

    override fun getAll(): Flow<List<Keyword>> = sharedFlow

    override suspend fun add(keyword: Keyword) {
        keywordList.add(keyword)
        sharedFlow.emit(keywordList)
    }

    override suspend fun remove(keyword: Keyword) {
        keywordList.remove(keyword)
        sharedFlow.emit(keywordList)
    }
}