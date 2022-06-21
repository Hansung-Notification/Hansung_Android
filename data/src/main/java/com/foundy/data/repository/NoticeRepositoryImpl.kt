package com.foundy.data.repository

import com.foundy.data.mapper.NoticeMapper
import com.foundy.data.repository.notice.NoticeRemoteDataSource
import com.foundy.domain.model.Notice
import com.foundy.domain.repository.NoticeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.Exception

class NoticeRepositoryImpl @Inject constructor(
    private val noticeRemoteDataSource: NoticeRemoteDataSource
) : NoticeRepository {

    override suspend fun getNoticeList(): Result<List<Notice>> {
        return try {
            val response = withContext(Dispatchers.IO) {
                noticeRemoteDataSource.getNoticeList()
            }

            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                Result.success(NoticeMapper(responseBody))
            } else {
                val errorMessage = response.message().toString()
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}