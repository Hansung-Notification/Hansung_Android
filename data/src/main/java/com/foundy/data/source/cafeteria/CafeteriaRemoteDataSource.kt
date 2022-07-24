package com.foundy.data.source.cafeteria

import okhttp3.ResponseBody
import retrofit2.Response

interface CafeteriaRemoteDataSource {
    suspend fun getStudentCafeteria(): Response<ResponseBody>
}