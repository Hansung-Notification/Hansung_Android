package com.foundy.data.repository.notice

import com.foundy.data.api.NoticeApi
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class NoticeRemoteDataSourceImpl @Inject constructor(
    private val noticeApi: NoticeApi
) : NoticeRemoteDataSource {

    override suspend fun getNoticeList(): Response<ResponseBody> {
        return noticeApi.getNoticeList()
    }
}