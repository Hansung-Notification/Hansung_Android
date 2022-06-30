package com.foundy.presentation.view.keyword

import androidx.lifecycle.*
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.foundy.presentation.model.KeywordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    readKeywordListUseCase: ReadKeywordListUseCase,
    private val addKeywordUseCase: AddKeywordUseCase,
    private val removeKeywordUseCase: RemoveKeywordUseCase
) : ViewModel() {

    val keywordList = readKeywordListUseCase().asLiveData().map { list ->
        list.map { createKeywordUiState(it) }
    }

    private fun createKeywordUiState(keyword: Keyword): KeywordUiState {
        return KeywordUiState(
            keyword = keyword.title,
            onClickDelete = { removeKeywordItem(keyword) }
        )
    }

    fun addKeywordItem(keyword: Keyword) {
        viewModelScope.launch {
            addKeywordUseCase(keyword)
        }
    }

    private fun removeKeywordItem(keyword: Keyword) {
        viewModelScope.launch {
            removeKeywordUseCase(keyword)
        }
    }
}