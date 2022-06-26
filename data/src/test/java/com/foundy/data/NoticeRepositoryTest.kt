package com.foundy.data

import com.foundy.data.api.NoticeApi
import com.foundy.data.paging.NoticePagingSource
import com.foundy.data.repository.NoticeRepositoryImpl
import com.foundy.data.repository.notice.NoticeRemoteDataSourceImpl
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

class NoticeRepositoryTest {

    @Mock
    lateinit var api: NoticeApi

    private lateinit var repository : NoticeRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        repository = NoticeRepositoryImpl(api, NoticeRemoteDataSourceImpl(api))
    }

    @Test
    fun `getHeaderNoticeList returns only headers correctly`() = runBlocking {
        val mockResponse = Response.success(
            ResponseBody.create(
                MediaType.get("text/html; charset=UTF-8"),
                fakeNoticeResponseContent
            )
        )
        given(api.getNoticeList(NoticePagingSource.START_PAGE)).willReturn(mockResponse)

        val result = repository.getHeaderNoticeList()
        assertEquals(result.getOrNull()!!.size, 2)
    }
}