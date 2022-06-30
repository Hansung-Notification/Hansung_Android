package com.foundy.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foundy.data.model.KEYWORD_TABLE_NAME
import com.foundy.data.model.KeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KeywordDao {
    @Query("SELECT * FROM $KEYWORD_TABLE_NAME")
    fun getAll(): Flow<List<KeywordEntity>>

    @Insert
    suspend fun insert(entity: KeywordEntity)

    @Delete
    suspend fun delete(entity: KeywordEntity)
}