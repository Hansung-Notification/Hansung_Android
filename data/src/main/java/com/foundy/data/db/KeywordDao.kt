package com.foundy.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foundy.domain.model.KEYWORD_TABLE_NAME
import com.foundy.domain.model.Keyword

@Dao
interface KeywordDao {
    @Query("SELECT * FROM $KEYWORD_TABLE_NAME")
    suspend fun getAll(): List<Keyword>

    @Insert
    suspend fun insert(keyword: Keyword)

    @Delete
    suspend fun delete(keyword: Keyword)
}