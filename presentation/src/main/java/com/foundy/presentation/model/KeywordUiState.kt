package com.foundy.presentation.model

import com.foundy.domain.model.Keyword

data class KeywordUiState (
    val keywordList: List<Keyword> = emptyList(),
    val error: Throwable? = null,
    val isLoadingKeywords: Boolean = true
)