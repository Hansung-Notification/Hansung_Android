package com.foundy.hansungnotification.fake

import com.foundy.domain.repository.FirebaseRepository

class FakeFirebaseRepositoryImpl: FirebaseRepository {
    override fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {

    }

    override fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {

    }
}