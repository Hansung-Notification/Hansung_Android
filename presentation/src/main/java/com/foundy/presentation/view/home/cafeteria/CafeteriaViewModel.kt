package com.foundy.presentation.view.home.cafeteria

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foundy.domain.usecase.cafeteria.GetWeeklyCafeteriaUseCase
import com.foundy.presentation.model.CafeteriaUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CafeteriaViewModel @Inject constructor(
    private val getWeeklyCafeteriaUseCase: GetWeeklyCafeteriaUseCase
) : ViewModel() {

    var uiState: CafeteriaUiState by mutableStateOf(CafeteriaUiState.Loading)
        private set

    private var job: Job? = null

    init {
        fetchCafeteriaData()
    }

    fun fetchCafeteriaData() {
        uiState = CafeteriaUiState.Loading
        job?.cancel()
        job = viewModelScope.launch {
            val result = getWeeklyCafeteriaUseCase()
            uiState = if (result.isSuccess) {
                CafeteriaUiState.Success(result.getOrNull()!!)
            } else {
                CafeteriaUiState.Failure(result.exceptionOrNull()!!)
            }
        }
    }
}