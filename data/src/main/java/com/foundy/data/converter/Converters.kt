package com.foundy.data.converter

import com.foundy.data.model.FavoriteNoticeEntity
import com.foundy.data.model.QueryEntity
import com.foundy.domain.model.Notice
import com.foundy.domain.model.Query

fun FavoriteNoticeEntity.toNotice() = Notice(
    isHeader = false,
    isNew = false,
    title = title,
    date = date,
    writer = writer,
    url = url
)

fun Notice.toFavoriteNoticeEntity() = FavoriteNoticeEntity(
    title = title,
    date = date,
    writer = writer,
    url = url
)

fun QueryEntity.toModel() = Query(
    content = content,
    date = date
)

fun Query.toEntity() = QueryEntity(
    content = content,
    date = date
)