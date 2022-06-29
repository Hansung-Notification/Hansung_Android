package com.foundy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foundy.domain.model.Keyword

@Database(entities = [Keyword::class], version = 1)
abstract class KeywordDatabase : RoomDatabase() {
    abstract fun keywordDao(): KeywordDao
}