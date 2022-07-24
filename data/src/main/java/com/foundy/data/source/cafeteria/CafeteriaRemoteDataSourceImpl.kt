package com.foundy.data.source.cafeteria

import com.foundy.data.api.CafeteriaApi
import javax.inject.Inject

class CafeteriaRemoteDataSourceImpl @Inject constructor(
    private val api: CafeteriaApi
) : CafeteriaRemoteDataSource {

    override suspend fun getStudentCafeteria() = api.getStudentCafeteria()
}