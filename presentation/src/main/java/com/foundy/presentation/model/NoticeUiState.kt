package com.foundy.presentation.model

import androidx.paging.PagingData

data class NoticeUiState(
    val noticeItemPagingData: PagingData<NoticeItemUiState> = PagingData.empty()
)
