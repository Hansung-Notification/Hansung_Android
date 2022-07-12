package com.foundy.hansungnotification.fake

import com.foundy.domain.repository.AuthRepository

class FakeAuthRepositoryImpl(private var isSignedIn: Boolean = true) : AuthRepository {

    fun setSignedIn(isSignedIn: Boolean) {
        this.isSignedIn = isSignedIn
    }

    override fun isSignedIn(): Boolean {
        return isSignedIn
    }

    override fun signInWith(idToken: String, onComplete: (result: Result<Any>) -> Unit) {

    }
}