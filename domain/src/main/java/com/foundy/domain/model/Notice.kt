package com.foundy.domain.model

import com.google.gson.annotations.SerializedName

data class Notice(
    @SerializedName("isHeader") val isHeader: Boolean,
    @SerializedName("isNew") val isNew: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("date") val date: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("url") val url: String
)
