package com.foundy.data.repository

import com.foundy.domain.repository.FirebaseRepository
import com.github.kimcore.inko.Inko.Companion.asEnglish
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class FirebaseRepositoryImpl : FirebaseRepository {

    private val keywordsReference = Firebase.database.reference.child("keywords")

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
}