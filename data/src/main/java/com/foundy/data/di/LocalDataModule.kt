package com.foundy.data.di

import android.content.Context
import androidx.room.Room
import com.foundy.data.db.FavoriteDao
import com.foundy.data.db.HansungDatabase
import com.foundy.data.db.KeywordDao
import com.foundy.data.source.favorite.FavoriteLocalDataSource
import com.foundy.data.source.favorite.FavoriteLocalDataSourceImpl
import com.foundy.data.source.keyword.KeywordLocalDataSource
import com.foundy.data.source.keyword.KeywordLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    companion object {
        const val DB_NAME = "hansung_notification"
    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context): HansungDatabase {
        return Room.databaseBuilder(
            context,
            HansungDatabase::class.java,
            DB_NAME
        ).build()
    }

    // Favorite
    @Provides
    @Singleton
    fun provideFavoriteDao(hansungDatabase: HansungDatabase): FavoriteDao {
        return hansungDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteLocalDataSource(favoriteDao: FavoriteDao): FavoriteLocalDataSource {
        return FavoriteLocalDataSourceImpl(favoriteDao)
    }
    // ~Favorite

    // Keyword
    @Provides
    @Singleton
    fun provideKeywordDao(hansungDatabase: HansungDatabase): KeywordDao {
        return hansungDatabase.keywordDao()
    }

    @Provides
    @Singleton
    fun provideKeywordLocalDataSource(keywordDao: KeywordDao): KeywordLocalDataSource {
        return KeywordLocalDataSourceImpl(keywordDao)
    }
    // ~Keyword
}