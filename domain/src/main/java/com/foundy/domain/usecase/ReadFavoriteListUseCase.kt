package com.foundy.domain.usecase

import com.foundy.domain.model.Notice
import com.foundy.domain.repository.FavoriteRepository
import javax.inject.Inject

class ReadFavoriteListUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    operator fun invoke(): Result<List<Notice>> = favoriteRepository.getAll()
}