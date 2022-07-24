package com.foundy.presentation.view.home.cafeteria

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.foundy.domain.model.cafeteria.CafeteriaData
import com.foundy.presentation.model.CafeteriaUiState
import com.foundy.presentation.view.common.FailureContent
import com.foundy.presentation.view.common.LoadingContent

@Composable
fun CafeteriaScreen(viewModel: CafeteriaViewModel) {
    when (val uiState = viewModel.uiState) {
        is CafeteriaUiState.Success -> {
            CafeteriaContent(uiState.data)
        }
        is CafeteriaUiState.Failure -> {
            FailureContent(
                e = uiState.e,
                onClickRetry = viewModel::fetchCafeteriaData
            )
        }
        is CafeteriaUiState.Loading -> {
            LoadingContent()
        }
    }
}

@Composable
fun CafeteriaContent(data: CafeteriaData) {
    Text(data.toString())
}
