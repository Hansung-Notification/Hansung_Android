package com.foundy.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface NoticeApi {
    @GET("subview.do")
    suspend fun getNoticeList(): Response<ResponseBody>
}