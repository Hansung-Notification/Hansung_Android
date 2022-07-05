package com.foundy.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val QUERY_TABLE_NAME = "q_t_name"

@Entity(tableName = QUERY_TABLE_NAME)
data class QueryEntity(
    @ColumnInfo(name = "date") val date: String,
    @PrimaryKey @ColumnInfo(name = "content") val content: String,
)