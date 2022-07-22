package com.foundy.presentation.mapper

import com.foundy.domain.model.NoticeWithState
import com.foundy.presentation.model.NoticeItemUiState

fun NoticeWithState.toUiState() : NoticeItemUiState {
    return NoticeItemUiState(
        notice = notice,
        onClickFavorite = onClickFavorite,
        isFavorite = isFavorite
    )
}