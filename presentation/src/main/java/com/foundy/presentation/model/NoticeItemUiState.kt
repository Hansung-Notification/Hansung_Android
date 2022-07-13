package com.foundy.presentation.model

import com.foundy.domain.model.Notice

data class NoticeItemUiState(
    val notice: Notice,
    val onClickFavorite: (isFavorite: Boolean) -> Unit,
    val isFavorite: () -> Boolean
)
