package com.foundy.domain.usecase.auth

import com.foundy.domain.repository.AuthRepository
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.isSignedIn()
}