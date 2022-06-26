package com.foundy.data

import androidx.paging.PagingSource
import com.foundy.data.api.NoticeApi
import com.foundy.data.mapper.NoticeMapper
import com.foundy.data.source.notice.NoticePagingSource
import com.foundy.domain.exception.ScrapingException
import com.foundy.domain.model.Notice
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import retrofit2.HttpException
import retrofit2.Response

class NoticePagingSourceTest {

    @Mock
    lateinit var api: NoticeApi

    private lateinit var noticePagingSource: NoticePagingSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        noticePagingSource = NoticePagingSource(api)
    }

    @Test
    fun `returns failure if http error`() = runBlocking {
        val responseBody = ResponseBody.create(MediaType.get("text/html; charset=UTF-8"), "")
        val mockResponse = Response.error<ResponseBody>(404, responseBody)
        given(api.getNoticeList(any())).willReturn(mockResponse)

        val expectedResult = PagingSource.LoadResult.Error<Int, Notice>(HttpException(mockResponse))
        val actualResult = noticePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 30,
                placeholdersEnabled = false
            )
        )
        assertEquals(expectedResult.toString(), actualResult.toString())
    }

    @Test
    fun `returns failure if received null`() = runBlocking {
        given(api.getNoticeList(any())).willReturn(null)

        val expectedResult = PagingSource.LoadResult.Error<Int, Notice>(NullPointerException())
        val actualResult = noticePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )
        assertEquals(expectedResult.toString(), actualResult.toString())
    }

    @Test
    fun `refreshes successfully`() = runBlocking {
        val createMockResponse = {
            Response.success(
                ResponseBody.create(
                    MediaType.get("text/html; charset=UTF-8"),
                    fakeNoticeResponseContent
                )
            )
        }
        given(api.getNoticeList(1)).willReturn(createMockResponse())

        val expectedResult = PagingSource.LoadResult.Page(
            data = NoticeMapper(createMockResponse().body()!!),
            prevKey = null,
            nextKey = 2
        )
        assertEquals(
            expectedResult,
            noticePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 1,
                    loadSize = 30,
                    placeholdersEnabled = false
                )
            )
        )
    }

    @Test
    fun `fails to refresh if NoticeMapper is invalid`(): Unit = runBlocking {
        val mockResponse = Response.success(ResponseBody.create(
            MediaType.get("text/html; charset=UTF-8"),
            ""
        ))
        given(api.getNoticeList(any())).willReturn(mockResponse)

        val expectedResult = PagingSource.LoadResult.Error<Int, Notice>(ScrapingException())
        assertEquals(
            expectedResult.toString(),
            noticePagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }
}