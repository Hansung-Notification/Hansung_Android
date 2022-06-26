package com.foundy.data.di

import com.foundy.data.api.NoticeApi
import com.foundy.data.repository.notice.NoticeRemoteDataSource
import com.foundy.data.repository.notice.NoticeRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideNoticeDataSource(noticeApi: NoticeApi): NoticeRemoteDataSource {
        return NoticeRemoteDataSourceImpl(noticeApi)
    }
}