package com.foundy.data.source.notice

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

class SearchingNoticePagingSource @Inject constructor(
    private val noticeApi: NoticeApi,
    private val query: String
) : PagingSource<Int, Notice>() {

    companion object {
        const val START_PAGE = 1
    }

    /**
     * Post 요청에 쓰일 파라미터를 생성한다.
     *
     * 만약 추후에 한성대 사이트가 리뉴얼 되는 경우 크롬 검사에 들어가 네트워크 탭에서 해당 파일을 고른 후 페이로드 항목을
     * 확인하면 된다.
     */
    private fun createParameter() : HashMap<String, Any> {
        return hashMapOf(
            "srchWrd" to query,
            "srchColumn" to "sj"
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notice> {
        val page = params.key ?: START_PAGE
        return try {
            val response = withContext(Dispatchers.IO) {
                noticeApi.searchNoticeList(
                    page = page,
                    param = createParameter()
                )
            }
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                LoadResult.Page(
                    data = NoticeMapper(responseBody).filter { !it.isHeader },
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