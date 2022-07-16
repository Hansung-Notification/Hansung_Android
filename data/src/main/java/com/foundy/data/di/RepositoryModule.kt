package com.foundy.data.di

import com.foundy.data.api.NoticeApi
import com.foundy.data.reference.DatabaseReferenceGetter
import com.foundy.data.repository.*
import com.foundy.data.source.favorite.FavoriteLocalDataSource
import com.foundy.data.source.query.QueryLocalDataSource
import com.foundy.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    fun provideKeywordRepository(
        @Named("keywords") keywordsReferenceGetter: DatabaseReferenceGetter,
        @Named("userKeywords") userKeywordsReferenceGetter: DatabaseReferenceGetter
    ): KeywordRepository {
        return KeywordRepositoryImpl(keywordsReferenceGetter, userKeywordsReferenceGetter)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideMessagingRepository(
        @Named("keywords") keywordsReferenceGetter: DatabaseReferenceGetter,
        @Named("userKeywords") userKeywordsReferenceGetter: DatabaseReferenceGetter
    ): MessagingRepository {
        return MessagingRepositoryImpl(keywordsReferenceGetter, userKeywordsReferenceGetter)
    }

    @Provides
    fun provideQueryRepository(queryLocalDataSource: QueryLocalDataSource): QueryRepository {
        return QueryRepositoryImpl(queryLocalDataSource)
    }
}