package com.foundy.hansungnotification.fake

import android.util.Log
import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking

class FakeKeywordRepositoryImpl : KeywordRepository {

    private var sharedFlow = MutableSharedFlow<Result<List<Keyword>>>()
    private val keywordList = mutableListOf<Keyword>()

    suspend fun setFakeList(keywords: List<Keyword>) {
        keywordList.clear()
        keywordList.addAll(keywords)
        emitFake()
    }

    suspend fun setFailedResult() {
        sharedFlow.emit(Result.failure(Exception()))
    }

    private suspend fun emitFake() {
        sharedFlow.emit(Result.success(keywordList))
    }

    override fun getAll(): Flow<Result<List<Keyword>>> = sharedFlow

    override fun add(keyword: Keyword) {
        keywordList.add(keyword)
        runBlocking { emitFake() }
    }

    override fun remove(keyword: Keyword) {
        keywordList.remove(keyword)
        runBlocking { emitFake() }
    }
}