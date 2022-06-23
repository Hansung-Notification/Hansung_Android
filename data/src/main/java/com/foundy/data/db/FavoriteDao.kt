package com.foundy.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.foundy.domain.model.NOTICE_TABLE_NAME
import com.foundy.domain.model.Notice

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM $NOTICE_TABLE_NAME")
    fun getAll(): List<Notice>

    @Insert
    fun insert(notice: Notice)

    @Delete
    fun delete(notice: Notice)
}