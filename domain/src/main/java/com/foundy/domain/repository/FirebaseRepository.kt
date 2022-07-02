package com.foundy.domain.repository


interface FirebaseRepository {
    fun subscribeTo(topic: String, onFailure: (Exception) -> Unit)
    fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit)
}