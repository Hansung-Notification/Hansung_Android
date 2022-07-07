package com.foundy.data.source.notice

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.foundy.data.api.NoticeApi
import com.foundy.data.constant.WebConstant
import com.foundy.data.constant.WebConstant.START_PAGE
import com.foundy.data.mapper.NoticeMapper
import com.foundy.domain.model.Notice
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject

class SearchingNoticePagingSource @Inject constructor(
    private val noticeApi: NoticeApi,
    private val query: String
) : PagingSource<Int, Notice>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notice> {
        val page = params.key ?: START_PAGE
        return try {
            val response = noticeApi.searchNoticeList(
                page = page,
                param = WebConstant.createSearchPostParameter(query)
            )
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val noticesResult = NoticeMapper(responseBody).filter { !it.isHeader }
                val isEnd = noticesResult.isEmpty()

                LoadResult.Page(
                    data = noticesResult,
                    prevKey = if (page == START_PAGE) null else page - 1,
                    nextKey = if (isEnd) null else page + 1
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