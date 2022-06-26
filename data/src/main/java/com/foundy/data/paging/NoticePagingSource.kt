package com.foundy.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.foundy.data.api.NoticeApi
import com.foundy.data.mapper.NoticeMapper
import com.foundy.domain.model.Notice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class NoticePagingSource @Inject constructor(
    private val noticeApi: NoticeApi
) : PagingSource<Int, Notice>() {

    companion object {
        const val START_PAGE = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notice> {
        val page = params.key ?: START_PAGE
        return try {
            val response = withContext(Dispatchers.IO) {
                noticeApi.getNoticeList(page)
            }
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                LoadResult.Page(
                    data = NoticeMapper.NonHeader(responseBody),
                    prevKey = if (page == START_PAGE) null else page - 1,
                    nextKey = page + 1
                )
            } else {
                throw HttpException(response)
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Notice>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}