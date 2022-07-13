package com.foundy.presentation.view.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.messaging.SubscribeAllDbKeywordsUseCase
import com.foundy.presentation.view.common.NoticeUiStateCreatorFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    readFavoriteListUseCase: ReadFavoriteListUseCase,
    private val subscribeAllDbKeywordsUseCase: SubscribeAllDbKeywordsUseCase,
    noticeUiStateCreatorFactory: NoticeUiStateCreatorFactory
) : ViewModel() {

    private val noticeUiStateCreator = noticeUiStateCreatorFactory.create(viewModelScope)

    val favoritesState = readFavoriteListUseCase().map { list ->
        list.map(noticeUiStateCreator::create)
    }

    val noticeFlow = getNoticeListUseCase().cachedIn(viewModelScope).map { pagingData ->
        pagingData.map(noticeUiStateCreator::create)
    }

    fun subscribeAllDbKeywords() {
        try {
            subscribeAllDbKeywordsUseCase()
        } catch (e: NotSignedInException) {
        }
    }
}