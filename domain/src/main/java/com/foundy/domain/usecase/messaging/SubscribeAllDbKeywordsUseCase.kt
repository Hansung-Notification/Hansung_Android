package com.foundy.domain.usecase.messaging

import com.foundy.domain.repository.MessagingRepository
import javax.inject.Inject

class SubscribeAllDbKeywordsUseCase @Inject constructor(
    private val repository: MessagingRepository
) {
    operator fun invoke() {
        repository.subscribeAllDbKeywords()
    }
}