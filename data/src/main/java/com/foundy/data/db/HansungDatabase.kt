package com.foundy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foundy.data.model.FavoriteNoticeEntity
import com.foundy.data.model.QueryEntity

@Database(
    entities = [
        FavoriteNoticeEntity::class,
        QueryEntity::class
    ],
    version = 1
)
abstract class HansungDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun queryDao(): QueryDao
}