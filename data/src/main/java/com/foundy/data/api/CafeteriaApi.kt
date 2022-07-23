package com.foundy.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface CafeteriaApi {
    @GET("hansung/1920/subview.do")
    suspend fun getStudentCafeteria(): Response<ResponseBody>
}