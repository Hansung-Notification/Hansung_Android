package com.foundy.domain.usecase

import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import javax.inject.Inject

class AddFavoriteNoticeUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(notice: Notice) = repository.add(notice)
}