package com.foundy.domain.repository

interface MessagingRepository {
    fun subscribeAllPreviousTopic()
    fun subscribeTo(topic: String, onFailure: (Exception) -> Unit)
    fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit)
}