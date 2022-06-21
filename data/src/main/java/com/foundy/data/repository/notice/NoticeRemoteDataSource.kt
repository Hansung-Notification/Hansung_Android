package com.foundy.data.repository.notice

import okhttp3.ResponseBody
import retrofit2.Response

interface NoticeRemoteDataSource {
    suspend fun getNoticeList(): Response<ResponseBody>
}