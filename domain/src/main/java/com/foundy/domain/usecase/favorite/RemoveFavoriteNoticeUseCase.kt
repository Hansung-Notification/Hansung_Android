package com.foundy.domain.usecase.favorite

import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFavoriteNoticeUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(notice: Notice) = repository.remove(notice)
}