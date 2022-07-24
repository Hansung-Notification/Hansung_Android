package com.foundy.presentation.model

import com.foundy.domain.model.cafeteria.CafeteriaData

sealed class CafeteriaUiState {
    class Success(val data: CafeteriaData) : CafeteriaUiState()
    class Failure(val e: Throwable) : CafeteriaUiState()
    object Loading : CafeteriaUiState()
}