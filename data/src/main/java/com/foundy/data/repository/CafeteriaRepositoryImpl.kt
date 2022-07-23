package com.foundy.data.repository

import com.foundy.data.mapper.CafeteriaMapper
import com.foundy.data.source.cafeteria.CafeteriaRemoteDataSource
import com.foundy.domain.model.cafeteria.CafeteriaData
import com.foundy.domain.repository.CafeteriaRepository
import retrofit2.HttpException
import javax.inject.Inject

class CafeteriaRepositoryImpl @Inject constructor(
    private val dataSource: CafeteriaRemoteDataSource
) : CafeteriaRepository {

    override suspend fun getWeekly(): Result<CafeteriaData> {
        return try {
            val response = dataSource.getStudentCafeteria()
            if (response.isSuccessful) {
                val data = CafeteriaMapper(responseBody = response.body()!!)
                Result.success(data)
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}