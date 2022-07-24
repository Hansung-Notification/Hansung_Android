package com.foundy.domain.usecase.cafeteria

import com.foundy.domain.repository.CafeteriaRepository
import javax.inject.Inject

class GetWeeklyCafeteriaUseCase @Inject constructor(
    private val repository: CafeteriaRepository
) {
    suspend operator fun invoke() = repository.getWeekly()
}