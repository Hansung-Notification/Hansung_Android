package com.foundy.presentation.view.home

import androidx.lifecycle.*
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.messaging.SubscribeAllDbKeywordsUseCase
import com.foundy.presentation.view.common.NoticeItemUiStateCreatorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    readFavoriteListUseCase: ReadFavoriteListUseCase,
    private val subscribeAllDbKeywordsUseCase: SubscribeAllDbKeywordsUseCase,
    noticeItemUiStateCreatorFactory: NoticeItemUiStateCreatorFactory
) : ViewModel() {

    private val noticeUiStateCreator = noticeItemUiStateCreatorFactory.create(viewModelScope)

    val favoritesState = readFavoriteListUseCase().map { list ->
        list.map(noticeUiStateCreator::create)
    }

    fun subscribeAllDbKeywords() {
        try {
            subscribeAllDbKeywordsUseCase()
        } catch (e: NotSignedInException) {
        }
    }
}