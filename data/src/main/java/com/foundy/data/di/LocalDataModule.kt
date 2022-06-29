package com.foundy.data.di

import android.content.Context
import androidx.room.Room
import com.foundy.data.db.FavoriteDao
import com.foundy.data.db.FavoriteDatabase
import com.foundy.data.db.KeywordDao
import com.foundy.data.db.KeywordDatabase
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

    // Favorite
    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(favoriteDatabase: FavoriteDatabase): FavoriteDao {
        return favoriteDatabase.favoriteDao()
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
    fun provideKeywordDatabase(@ApplicationContext context: Context): KeywordDatabase {
        return Room.databaseBuilder(
            context,
            KeywordDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideKeywordDao(keywordDatabase: KeywordDatabase): KeywordDao {
        return keywordDatabase.keywordDao()
    }

    @Provides
    @Singleton
    fun provideKeywordLocalDataSource(keywordDao: KeywordDao): KeywordLocalDataSource {
        return KeywordLocalDataSourceImpl(keywordDao)
    }
    // ~Keyword
}