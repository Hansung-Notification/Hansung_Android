package com.foundy.domain.model

data class Notice(
    val isHeader: Boolean,
    val isNew: Boolean,
    val title: String,
    val date: String,
    val writer: String,
    val url: String
)
