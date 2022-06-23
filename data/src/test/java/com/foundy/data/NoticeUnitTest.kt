package com.foundy.data

import com.foundy.data.di.NetworkModule
import com.foundy.data.repository.NoticeRepositoryImpl
import com.foundy.data.repository.notice.NoticeRemoteDataSourceImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class NoticeUnitTest {
    private val mockNoticeApiService = with(NetworkModule()) {
        provideNoticeApiService(provideRetrofit(provideHttpClient()))
    }

    @Test
    fun `get noticeList successfully`() {
        val repository = NoticeRepositoryImpl(NoticeRemoteDataSourceImpl(mockNoticeApiService))

        val result = runBlocking { repository.getNoticeList() }

        assertEquals(result.isSuccess, true)
    }
}