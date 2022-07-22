package com.foundy.presentation.view.home.favorite

import androidx.lifecycle.*
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.presentation.model.FavoriteUiState
import com.foundy.presentation.view.common.NoticeItemUiStateCreatorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    readFavoriteListUseCase: ReadFavoriteListUseCase,
    noticeItemUiStateCreatorFactory: NoticeItemUiStateCreatorFactory
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    private val noticeUiStateCreator = noticeItemUiStateCreatorFactory.create(
        viewModelScope,
        triggerCollection = false
    )

    init {
        viewModelScope.launch {
            readFavoriteListUseCase().map { list ->
                list.map(noticeUiStateCreator::create)
            }.collect { favorites ->
                _uiState.update { it.copy(favoriteItemList = favorites) }
            }
        }
    }
}