package com.foundy.presentation.view.home.favorite

import androidx.lifecycle.*
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.notice.CreateNoticeWithStateUseCase
import com.foundy.presentation.mapper.toUiState
import com.foundy.presentation.model.FavoriteUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    readFavoriteListUseCase: ReadFavoriteListUseCase,
    createNoticeWithStateUseCase: CreateNoticeWithStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            readFavoriteListUseCase().map { list ->
                list.map { createNoticeWithStateUseCase(it, viewModelScope).toUiState() }
            }.collect { favorites ->
                _uiState.update { it.copy(favoriteItemList = favorites) }
            }
        }
    }
}