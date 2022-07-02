package com.foundy.presentation.view.keyword

import androidx.lifecycle.*
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.firebase.SubscribeToUseCase
import com.foundy.domain.usecase.firebase.UnsubscribeFromUseCase
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    readKeywordListUseCase: ReadKeywordListUseCase,
    private val addKeywordUseCase: AddKeywordUseCase,
    private val removeKeywordUseCase: RemoveKeywordUseCase,
    private val subscribeToUseCase: SubscribeToUseCase,
    private val unsubscribeFromUseCase: UnsubscribeFromUseCase
) : ViewModel() {

    val keywordList = readKeywordListUseCase().asLiveData()

    fun addKeywordItem(keyword: Keyword) {
        viewModelScope.launch {
            addKeywordUseCase(keyword)
        }
    }

    fun removeKeywordItem(keyword: Keyword) {
        viewModelScope.launch {
            removeKeywordUseCase(keyword)
        }
    }

    fun hasKeyword(keyword: String): Boolean {
        return keywordList.value?.firstOrNull { it.title == keyword } != null
    }

    fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {
        subscribeToUseCase(topic, onFailure)
    }

    fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {
        unsubscribeFromUseCase(topic, onFailure)
    }
}