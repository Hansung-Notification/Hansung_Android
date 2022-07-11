package com.foundy.presentation.view.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.domain.usecase.messaging.SubscribeAllDbKeywordsUseCase
import com.foundy.presentation.view.common.FavoriteNoticeDelegate
import com.foundy.presentation.view.common.ViewModelFavoriteNoticeDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    readFavoriteListUseCase: ReadFavoriteListUseCase,
    addFavoriteNoticeUseCase: AddFavoriteNoticeUseCase,
    removeFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase,
    private val subscribeAllDbKeywordsUseCase: SubscribeAllDbKeywordsUseCase
) : ViewModel() {

    private val favoritesDelegate: FavoriteNoticeDelegate = ViewModelFavoriteNoticeDelegate(
        readFavoriteListUseCase,
        addFavoriteNoticeUseCase,
        removeFavoriteNoticeUseCase,
        viewModelScope
    )

    val favoritesState get() = favoritesDelegate.favoritesState

    val noticeFlow = getNoticeListUseCase().cachedIn(viewModelScope).map { pagingData ->
        pagingData.map { favoritesDelegate.createNoticeUiState(it) }
    }

    fun subScribeAllDbKeywords() {
        try {
            subscribeAllDbKeywordsUseCase()
        } catch (e: NotSignedInException) {
        }
    }
}