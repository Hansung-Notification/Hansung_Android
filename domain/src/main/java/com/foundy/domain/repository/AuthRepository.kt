package com.foundy.domain.repository

interface AuthRepository {
    fun isSignedIn(): Boolean
    fun signInWith(idToken: String, onComplete: (result: Result<Any>) -> Unit)
}