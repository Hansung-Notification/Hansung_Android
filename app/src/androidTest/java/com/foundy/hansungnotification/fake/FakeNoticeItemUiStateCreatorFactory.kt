package com.foundy.hansungnotification.fake

import com.foundy.domain.repository.FavoriteRepository
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.IsFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.presentation.view.common.NoticeItemUiStateCreator
import com.foundy.presentation.view.common.NoticeItemUiStateCreatorFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class FakeNoticeItemUiStateCreatorFactory(
    private val favoriteRepository: FavoriteRepository
) : NoticeItemUiStateCreatorFactory {

    override fun create(
        viewModelScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        triggerCollection: Boolean
    ) = NoticeItemUiStateCreator(
        ReadFavoriteListUseCase(favoriteRepository),
        AddFavoriteNoticeUseCase(favoriteRepository),
        RemoveFavoriteNoticeUseCase(favoriteRepository),
        IsFavoriteNoticeUseCase(favoriteRepository),
        viewModelScope,
        dispatcher,
        triggerCollection = true,
    )
}