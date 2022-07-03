package com.foundy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foundy.data.model.FavoriteNoticeEntity

@Database(
    entities = [
        FavoriteNoticeEntity::class,
    ],
    version = 1
)
abstract class HansungDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}