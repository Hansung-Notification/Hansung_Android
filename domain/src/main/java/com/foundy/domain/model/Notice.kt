package com.foundy.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val NOTICE_TABLE_NAME = "favorite"

@Entity(tableName = NOTICE_TABLE_NAME)
data class Notice(
    @ColumnInfo(name = "is_header") val isHeader: Boolean,
    @ColumnInfo(name = "is_new") val isNew: Boolean,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "writer") val writer: String,
    @PrimaryKey val url: String
)
