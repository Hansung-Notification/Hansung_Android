package com.foundy.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface NoticeApi {
    @GET("hansung/8385/subview.do")
    suspend fun getNoticeList(
        @Query("page") page: Int
    ): Response<ResponseBody>

    @FormUrlEncoded
    @POST("bbs/hansung/143/artclList.do")
    suspend fun searchNoticeList(
        @Query("page") page: Int,
        @Field("srchWrd") query: String,
        @Field("srchColumn") searchColumn: String = "sj"
    ): Response<ResponseBody>
}