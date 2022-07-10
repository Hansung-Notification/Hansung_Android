package com.foundy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foundy.domain.repository.AuthRepository
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthRepositoryImpl : AuthRepository {

    override fun isSignedIn(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override fun signInWith(idToken: String): LiveData<Result<Any>> {
        val result = MutableLiveData<Result<Any>>()
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.signInWithCredential(firebaseCredential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                result.value = Result.success(true)
            } else {
                result.value = Result.failure(task.exception!!)
            }
        }
        return result
    }
}