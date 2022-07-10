package com.foundy.domain.usecase.messaging

import com.foundy.domain.repository.MessagingRepository
import javax.inject.Inject

class SubscribeAllPreviousTopicUseCase @Inject constructor(
    private val repository: MessagingRepository
) {
    operator fun invoke() {
        repository.subscribeAllPreviousTopic()
    }
}