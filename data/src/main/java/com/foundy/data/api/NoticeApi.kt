package com.foundy.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NoticeApi {
    @GET("hansung/8385/subview.do")
    suspend fun getNoticeList(
        @Query("page") page: Int
    ): Response<ResponseBody>
}