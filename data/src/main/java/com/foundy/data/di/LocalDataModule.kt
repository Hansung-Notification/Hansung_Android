package com.foundy.data.di

import android.content.Context
import androidx.room.Room
import com.foundy.data.db.FavoriteDao
import com.foundy.data.db.FavoriteDatabase
import com.foundy.data.source.favorite.FavoriteLocalDataSource
import com.foundy.data.source.favorite.FavoriteLocalDataSourceImpl
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
}