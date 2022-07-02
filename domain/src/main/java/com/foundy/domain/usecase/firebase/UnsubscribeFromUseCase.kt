package com.foundy.domain.usecase.firebase

import com.foundy.domain.repository.FirebaseRepository
import javax.inject.Inject

class UnsubscribeFromUseCase @Inject constructor(
    private val repository: FirebaseRepository,
) {
    operator fun invoke(topic: String, onFailure: (Exception) -> Unit) {
        repository.unsubscribeFrom(topic, onFailure)
    }
}