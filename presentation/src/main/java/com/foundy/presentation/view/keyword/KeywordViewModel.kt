package com.foundy.presentation.view.keyword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val readKeywordListUseCase: ReadKeywordListUseCase,
    private val addKeywordUseCase: AddKeywordUseCase,
    private val removeKeywordUseCase: RemoveKeywordUseCase
) : ViewModel() {

    private val _keywordList = mutableListOf<KeywordUiState>()
    val keywordList: List<KeywordUiState> get() = _keywordList

    init {
        readKeywordList()
    }

    private fun createKeywordUiState(keyword: Keyword): KeywordUiState {
        return KeywordUiState(
            keyword = keyword.title,
            onClickDelete = { removeKeywordItem(keyword) }
        )
    }

    private fun readKeywordList() {
        viewModelScope.launch {
            val result = readKeywordListUseCase()
            if (result.isSuccess) {
                result.getOrNull()?.let { keywords ->
                    val states = keywords.map(::createKeywordUiState)
                    _keywordList.addAll(states)
                }
            }
        }
    }

    fun addKeywordItem(keyword: Keyword) {
        _keywordList.add(createKeywordUiState(keyword))
        viewModelScope.launch {
            addKeywordUseCase(keyword)
        }
    }

    private fun removeKeywordItem(keyword: Keyword) {
        _keywordList.removeAll { it.keyword == keyword.title }
        viewModelScope.launch {
            removeKeywordUseCase(keyword)
        }
    }
}