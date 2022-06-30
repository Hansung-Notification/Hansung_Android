package com.foundy.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val KEYWORD_TABLE_NAME = "keyword"

@Entity(tableName = KEYWORD_TABLE_NAME)
data class Keyword(
    @PrimaryKey @ColumnInfo(name = "title") val title: String
)