package com.foundy.domain.repository

import androidx.lifecycle.LiveData

interface FirebaseRepository {
    fun isSignedIn(): Boolean
    fun signInWith(idToken: String): LiveData<Result<Any>>
    fun subscribeAllPreviousTopic()
    fun subscribeTo(topic: String, onFailure: (Exception) -> Unit)
    fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit)
}