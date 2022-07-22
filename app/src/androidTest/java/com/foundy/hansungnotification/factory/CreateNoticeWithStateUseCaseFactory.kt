package com.foundy.hansungnotification.factory

import com.foundy.domain.repository.FavoriteRepository
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.IsFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.domain.usecase.notice.CreateNoticeWithStateUseCase

object CreateNoticeWithStateUseCaseFactory {

    fun create(favoriteRepository: FavoriteRepository): CreateNoticeWithStateUseCase {
        return CreateNoticeWithStateUseCase(
            AddFavoriteNoticeUseCase(favoriteRepository),
            RemoveFavoriteNoticeUseCase(favoriteRepository),
            IsFavoriteNoticeUseCase(favoriteRepository)
        )
    }
}