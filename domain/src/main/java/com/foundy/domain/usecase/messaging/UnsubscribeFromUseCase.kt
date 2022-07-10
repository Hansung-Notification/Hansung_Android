package com.foundy.domain.usecase.messaging

import com.foundy.domain.repository.MessagingRepository
import javax.inject.Inject

class UnsubscribeFromUseCase @Inject constructor(
    private val repository: MessagingRepository,
) {
    operator fun invoke(topic: String, onFailure: (Exception) -> Unit) {
        repository.unsubscribeFrom(topic, onFailure)
    }
}