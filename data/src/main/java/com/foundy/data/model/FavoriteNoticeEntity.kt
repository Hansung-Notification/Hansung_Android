package com.foundy.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAVORITE_NOTICE_TABLE_NAME = "favorite_notice"

@Entity(tableName = FAVORITE_NOTICE_TABLE_NAME)
data class FavoriteNoticeEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "writer") val writer: String,
    @PrimaryKey @ColumnInfo(name = "url") val url: String
)
