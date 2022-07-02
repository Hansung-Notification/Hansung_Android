package com.foundy.data.di

import com.foundy.data.api.NoticeApi
import com.foundy.data.repository.FavoriteRepositoryImpl
import com.foundy.data.repository.FirebaseRepositoryImpl
import com.foundy.data.repository.KeywordRepositoryImpl
import com.foundy.data.repository.NoticeRepositoryImpl
import com.foundy.data.source.favorite.FavoriteLocalDataSource
import com.foundy.data.source.keyword.KeywordLocalDataSource
import com.foundy.domain.repository.FavoriteRepository
import com.foundy.domain.repository.FirebaseRepository
import com.foundy.domain.repository.KeywordRepository
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
    fun provideNoticeRepository(noticeApi: NoticeApi): NoticeRepository {
        return NoticeRepositoryImpl(noticeApi)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(favoriteLocalDataSource: FavoriteLocalDataSource): FavoriteRepository {
        return FavoriteRepositoryImpl(favoriteLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideKeywordRepository(keywordLocalDataSource: KeywordLocalDataSource): KeywordRepository {
        return KeywordRepositoryImpl(keywordLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideFirebaseRepositoryImpl(): FirebaseRepository {
        return FirebaseRepositoryImpl()
    }
}