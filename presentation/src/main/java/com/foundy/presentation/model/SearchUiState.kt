package com.foundy.presentation.model

import androidx.paging.PagingData

data class SearchUiState(
    val recentQueries: List<String> = emptyList(),
    val searchedNoticePagingData: PagingData<NoticeItemUiState> = PagingData.empty()
)