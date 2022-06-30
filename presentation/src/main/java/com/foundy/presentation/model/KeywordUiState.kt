package com.foundy.presentation.model

data class KeywordUiState(
    val keyword: String,
    val onClickDelete: () -> Unit,
)
