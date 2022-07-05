package com.foundy.data.db

import androidx.room.*
import com.foundy.data.model.QUERY_TABLE_NAME
import com.foundy.data.model.QueryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QueryDao {
    @Query("SELECT * FROM $QUERY_TABLE_NAME ORDER BY date DESC")
    fun getRecentList(): Flow<List<QueryEntity>>

    @Insert
    suspend fun insert(entity: QueryEntity)

    @Delete
    suspend fun delete(entity: QueryEntity)

    @Update
    suspend fun update(entity: QueryEntity)
}