package com.foundy.presentation.view.keyword

import androidx.lifecycle.*
import com.foundy.domain.exception.NoSearchResultException
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.firebase.IsSignedInUseCase
import com.foundy.domain.usecase.firebase.SubscribeToUseCase
import com.foundy.domain.usecase.firebase.UnsubscribeFromUseCase
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.foundy.domain.usecase.notice.HasSearchResultUseCase
import com.foundy.presentation.utils.KeywordValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    readKeywordListUseCase: ReadKeywordListUseCase,
    private val addKeywordUseCase: AddKeywordUseCase,
    private val removeKeywordUseCase: RemoveKeywordUseCase,
    private val subscribeToUseCase: SubscribeToUseCase,
    private val unsubscribeFromUseCase: UnsubscribeFromUseCase,
    private val isSignedInUseCase: IsSignedInUseCase,
    private val hasSearchResultUseCase: HasSearchResultUseCase
) : ViewModel() {

    val keywordList = readKeywordListUseCase().asLiveData()

    fun addKeywordItem(keyword: Keyword) {
        addKeywordUseCase(keyword)
    }

    fun removeKeywordItem(keyword: Keyword) {
        removeKeywordUseCase(keyword)
    }

    fun checkValid(keyword: String) {
        KeywordValidator.check(keyword, keywordList.value?.getOrNull() ?: emptyList())
    }

    fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {
        subscribeToUseCase(topic, onFailure)
    }

    fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {
        unsubscribeFromUseCase(topic, onFailure)
    }

    fun isSignedIn() = isSignedInUseCase()

    suspend fun checkKeywordHasSearchResult(keyword: String) {
        val result = hasSearchResultUseCase(keyword)
        if (result.isSuccess) {
            val hasSearchResult = result.getOrNull()!!
            if (!hasSearchResult) {
                throw NoSearchResultException()
            }
        } else {
            throw result.exceptionOrNull()!!
        }
    }
}