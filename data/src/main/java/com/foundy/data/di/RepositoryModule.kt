package com.foundy.data.di

import com.foundy.data.repository.NoticeRepositoryImpl
import com.foundy.data.repository.notice.NoticeRemoteDataSource
import com.foundy.domain.repository.NoticeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideNoticeRepository(noticeRemoteDataSource: NoticeRemoteDataSource): NoticeRepository {
        return NoticeRepositoryImpl(noticeRemoteDataSource)
    }
}