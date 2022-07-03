package com.foundy.hansungnotification.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foundy.domain.repository.FirebaseRepository

class FakeFirebaseRepositoryImpl(private var isSignedIn: Boolean = true) : FirebaseRepository {

    fun setSignedIn(isSignedIn: Boolean) {
        this.isSignedIn = isSignedIn
    }

    override fun isSignedIn(): Boolean {
        return isSignedIn
    }

    override fun signInWith(idToken: String): LiveData<Result<Any>> {
        return MutableLiveData()
    }

    override fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {

    }

    override fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {

    }
}