package com.foundy.data.repository.notice

import com.foundy.domain.model.Notice
import okhttp3.ResponseBody
import retrofit2.Response

interface NoticeRemoteDataSource {
    suspend fun getNoticeList(page: Int): Response<ResponseBody>
}