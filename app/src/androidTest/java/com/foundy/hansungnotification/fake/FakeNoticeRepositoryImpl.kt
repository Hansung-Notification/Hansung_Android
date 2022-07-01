package com.foundy.hansungnotification.fake

import androidx.paging.PagingData
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeNoticeRepositoryImpl: NoticeRepository {

    private val sharedFlow = MutableSharedFlow<PagingData<Notice>>()
    private val noticeList = mutableListOf<Notice>()

    fun setFakeList(notices: List<Notice>) {
        noticeList.clear()
        noticeList.addAll(notices)
    }

    suspend fun emitFake() {
        sharedFlow.emit(PagingData.from(noticeList))
    }

    override fun getNoticeList() = sharedFlow
}