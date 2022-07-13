package com.foundy.hansungnotification.fake

import com.foundy.domain.repository.FavoriteRepository
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.IsFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.presentation.view.common.NoticeUiStateCreator
import com.foundy.presentation.view.common.NoticeUiStateCreatorFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class FakeNoticeUiStateCreatorFactory(
    private val favoriteRepository: FavoriteRepository
) : NoticeUiStateCreatorFactory {

    override fun create(
        viewModelScope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
        enableCollect: Boolean
    ) = NoticeUiStateCreator(
        ReadFavoriteListUseCase(favoriteRepository),
        AddFavoriteNoticeUseCase(favoriteRepository),
        RemoveFavoriteNoticeUseCase(favoriteRepository),
        IsFavoriteNoticeUseCase(favoriteRepository),
        viewModelScope,
        dispatcher,
        enableCollect = true,
    )
}