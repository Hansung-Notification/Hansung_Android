package com.foundy.data.converter

import com.foundy.data.model.FavoriteNoticeEntity
import com.foundy.domain.model.Notice

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
