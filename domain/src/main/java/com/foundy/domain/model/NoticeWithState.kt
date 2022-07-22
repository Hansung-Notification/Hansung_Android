package com.foundy.domain.model

data class NoticeWithState(
    val notice: Notice,
    val onClickFavorite: (isFavorite: Boolean) -> Unit,
    val isFavorite: () -> Boolean
)