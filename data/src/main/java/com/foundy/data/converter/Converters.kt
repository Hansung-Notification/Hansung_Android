package com.foundy.data.converter

import com.foundy.data.model.FavoriteNoticeEntity
import com.foundy.data.model.KeywordEntity
import com.foundy.domain.model.Keyword
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

fun KeywordEntity.toDomain() = Keyword(title = title)

fun Keyword.toEntity() = KeywordEntity(title = title)
