package com.foundy.hansungnotification.fake

import com.foundy.domain.repository.MessagingRepository

class FakeMessagingRepositoryImpl : MessagingRepository {

    override fun subscribeAllPreviousTopic() {

    }

    override fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {

    }

    override fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {

    }
}