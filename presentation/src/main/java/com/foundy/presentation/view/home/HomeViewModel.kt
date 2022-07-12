package com.foundy.presentation.view.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.messaging.SubscribeAllDbKeywordsUseCase
import com.foundy.presentation.view.common.FavoriteViewModelDelegateFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    private val subscribeAllDbKeywordsUseCase: SubscribeAllDbKeywordsUseCase,
    favoriteDelegateFactory: FavoriteViewModelDelegateFactory
) : ViewModel() {

    private val favoritesDelegate = favoriteDelegateFactory.create(viewModelScope)

    val favoritesState get() = favoritesDelegate.favoritesState

    val noticeFlow = getNoticeListUseCase().cachedIn(viewModelScope).map { pagingData ->
        pagingData.map { favoritesDelegate.createNoticeUiState(it) }
    }

    fun subscribeAllDbKeywords() {
        try {
            subscribeAllDbKeywordsUseCase()
        } catch (e: NotSignedInException) {
        }
    }
}