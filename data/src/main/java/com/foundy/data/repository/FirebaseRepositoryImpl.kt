package com.foundy.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foundy.domain.repository.FirebaseRepository
import com.github.kimcore.inko.Inko.Companion.asEnglish
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging


class FirebaseRepositoryImpl : FirebaseRepository {

    private val keywordsReference = Firebase.database.reference.child("keywords")

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

    override fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {
        // 한글로된 토픽을 못쓰기 때문에 변환을 해준다.
        Firebase.messaging.subscribeToTopic(topic.asEnglish).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                keywordsReference.child(topic).setValue(ServerValue.increment(1))
                    .addOnCompleteListener { dbTask ->
                        if (!dbTask.isSuccessful) {
                            onFailure(dbTask.exception!!)
                        }
                    }
            } else if (!task.isSuccessful) {
                onFailure(task.exception!!)
            }
        }
    }

    override fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {
        // 한글로된 토픽을 못쓰기 때문에 변환을 해준다.
        Firebase.messaging.unsubscribeFromTopic(topic.asEnglish).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                keywordsReference.child(topic).setValue(ServerValue.increment(-1))
                    .addOnCompleteListener { dbTask ->
                        if (!dbTask.isSuccessful) {
                            onFailure(dbTask.exception!!)
                        }
                    }
            } else if (!task.isSuccessful) {
                onFailure(task.exception!!)
            }
        }
    }

    companion object {
        private const val SERVER_CLIENT_ID =
            "86099946857-q8hjdct33dbr1aqhdn7b9te9hcvde4ac.apps.googleusercontent.com"
    }
}