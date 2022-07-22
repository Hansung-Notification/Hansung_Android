package com.foundy.presentation.view.home.notice

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.usecase.notice.CreateNoticeWithStateUseCase
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.presentation.mapper.toUiState
import com.foundy.presentation.model.NoticeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    createNoticeWithStateUseCase: CreateNoticeWithStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoticeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getNoticeListUseCase().cachedIn(viewModelScope).map { pagingData ->
                pagingData.map { createNoticeWithStateUseCase(it, viewModelScope).toUiState() }
            }.collectLatest { pagingData ->
                _uiState.update { it.copy(noticeItemPagingData = pagingData) }
            }
        }
    }
}