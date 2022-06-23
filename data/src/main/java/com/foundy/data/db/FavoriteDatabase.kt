package com.foundy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foundy.domain.model.Notice

@Database(entities = [Notice::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}