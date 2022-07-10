package com.foundy.domain.usecase.messaging

import com.foundy.domain.repository.MessagingRepository
import javax.inject.Inject

class SubscribeToUseCase @Inject constructor(
    private val repository: MessagingRepository,
) {
    operator fun invoke(topic: String, onFailure: (Exception) -> Unit) {
        repository.subscribeTo(topic, onFailure)
    }
}