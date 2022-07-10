package com.foundy.domain.usecase.auth

import com.foundy.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(idToken: String) = repository.signInWith(idToken)
}