package com.foundy.hansungnotification.fake

import androidx.paging.PagingData
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow

class FakeNoticeRepositoryImpl: NoticeRepository {

    private val sharedFlow = MutableSharedFlow<PagingData<Notice>>()
    private val noticeList = mutableListOf<Notice>()
    private var hasSearchResult = Result.success(true)

    fun setFakeList(notices: List<Notice>) {
        noticeList.clear()
        noticeList.addAll(notices)
    }

    fun setHasSearchResult(result: Result<Boolean>) {
        hasSearchResult = result
    }

    suspend fun emitFake() {
        sharedFlow.emit(PagingData.from(noticeList))
    }

    override fun getNoticeList() = sharedFlow

    override fun searchNoticeList(query: String) = emptyFlow<PagingData<Notice>>()

    override suspend fun hasSearchResult(query: String) = hasSearchResult
}