package com.foundy.domain.usecase.firebase

import com.foundy.domain.repository.FirebaseRepository
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke() = repository.isSignedIn()
}