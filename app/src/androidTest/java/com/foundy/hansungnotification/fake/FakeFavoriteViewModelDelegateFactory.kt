package com.foundy.hansungnotification.fake

import com.foundy.domain.repository.FavoriteRepository
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.presentation.view.common.FavoriteViewModelDelegate
import com.foundy.presentation.view.common.FavoriteViewModelDelegateFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

class FakeFavoriteViewModelDelegateFactory(
    private val favoriteRepository: FavoriteRepository
) : FavoriteViewModelDelegateFactory {

    override fun create(
        viewModelScope: CoroutineScope,
        dispatcher: CoroutineDispatcher
    ) = FavoriteViewModelDelegate(
        ReadFavoriteListUseCase(favoriteRepository),
        AddFavoriteNoticeUseCase(favoriteRepository),
        RemoveFavoriteNoticeUseCase(favoriteRepository),
        viewModelScope,
        dispatcher
    )
}