package com.foundy.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foundy.data.model.KEYWORD_TABLE_NAME
import com.foundy.data.model.KeywordEntity

@Dao
interface KeywordDao {
    @Query("SELECT * FROM $KEYWORD_TABLE_NAME")
    suspend fun getAll(): List<KeywordEntity>

    @Insert
    suspend fun insert(entity: KeywordEntity)

    @Delete
    suspend fun delete(entity: KeywordEntity)
}