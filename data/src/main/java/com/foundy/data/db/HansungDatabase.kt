package com.foundy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foundy.data.model.FavoriteNoticeEntity
import com.foundy.data.model.KeywordEntity

@Database(
    entities = [
        FavoriteNoticeEntity::class,
        KeywordEntity::class
    ],
    version = 1
)
abstract class HansungDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun keywordDao(): KeywordDao
}