package com.foundy.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface NoticeApi {
    @GET("hansung/8385/subview.do")
    suspend fun getNoticeList(): Response<ResponseBody>
}