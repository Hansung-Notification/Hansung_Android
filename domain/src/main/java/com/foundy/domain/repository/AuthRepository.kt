package com.foundy.domain.repository

import androidx.lifecycle.LiveData

interface AuthRepository {
    fun isSignedIn(): Boolean
    fun signInWith(idToken: String): LiveData<Result<Any>>
}