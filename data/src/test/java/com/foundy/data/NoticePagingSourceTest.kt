package com.foundy.data

import androidx.paging.PagingSource
import com.foundy.data.api.NoticeApi
import com.foundy.data.mapper.NoticeMapper
import com.foundy.data.source.notice.NoticePagingSource
import com.foundy.domain.exception.ScrapingException
import com.foundy.domain.model.Notice
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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

@OptIn(ExperimentalCoroutinesApi::class)
class NoticePagingSourceTest {

    @Mock
    lateinit var api: NoticeApi

    private lateinit var noticePagingSource: NoticePagingSource

    private val mediaType = MediaType.get("text/html; charset=UTF-8")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        noticePagingSource = NoticePagingSource(api)
    }

    @Test
    fun `returns failure if http error`() = runTest {
        val responseBody = ResponseBody.create(mediaType, "")
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
    fun `refreshes successfully`() = runTest {
        val createMockResponse = {
            Response.success(ResponseBody.create(mediaType, fakeNoticeResponseContent))
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
    fun `fails to refresh if NoticeMapper is invalid`() = runTest {
        val mockResponse = Response.success(ResponseBody.create(mediaType, ""))
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