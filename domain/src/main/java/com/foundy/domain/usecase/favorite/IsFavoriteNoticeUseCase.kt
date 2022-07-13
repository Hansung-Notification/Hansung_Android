package com.foundy.domain.usecase.favorite

import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import javax.inject.Inject

class IsFavoriteNoticeUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke(notice: Notice) = repository.isFavorite(notice)
}