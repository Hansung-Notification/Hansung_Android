package com.foundy.hansungnotification.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foundy.domain.repository.FirebaseRepository

class FakeFirebaseRepositoryImpl : FirebaseRepository {

    override fun isSignedIn(): Boolean {
        return true
    }

    override fun signInWith(idToken: String): LiveData<Result<Any>> {
        return MutableLiveData()
    }

    override fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {

    }

    override fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {

    }
}