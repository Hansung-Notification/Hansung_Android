package com.foundy.data.di

import com.foundy.data.api.NoticeApi
import com.foundy.data.repository.*
import com.foundy.data.source.favorite.FavoriteLocalDataSource
import com.foundy.data.source.query.QueryLocalDataSource
import com.foundy.domain.repository.*
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
    fun provideKeywordRepository(): KeywordRepository {
        return KeywordRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideFirebaseRepository(): FirebaseRepository {
        return FirebaseRepositoryImpl()
    }

    @Provides
    fun provideQueryRepository(queryLocalDataSource: QueryLocalDataSource): QueryRepository {
        return QueryRepositoryImpl(queryLocalDataSource)
    }
}