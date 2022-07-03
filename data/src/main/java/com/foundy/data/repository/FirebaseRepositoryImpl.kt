package com.foundy.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foundy.data.constant.FirebaseConstant.USERS
import com.foundy.data.constant.FirebaseConstant.KEYWORDS
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.repository.FirebaseRepository
import com.github.kimcore.inko.Inko.Companion.asEnglish
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class FirebaseRepositoryImpl : FirebaseRepository {

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

    /**
     * 앱 설치 이전에 저장한 토픽들을 다시 구독한다.
     *
     * 앱을 제거하면 구독이 취소되기 때문에 앱 설치 후 재로그인시 이 함수를 호출해야한다. 만약 재구독에 실패한다면 해당 키워드는
     * 삭제된다. 만약 이전 항목을 얻어오는 것 자체를 실패한다면 예외를 던진다.
     */
    override fun subscribeAllPreviousTopic() {
        val uid = Firebase.auth.uid ?: throw NotSignedInException()
        val dbReference = Firebase.database.reference
        val keywordReference = dbReference.child(KEYWORDS)
        val userKeywordsReference = dbReference.child(USERS).child(uid).child(KEYWORDS)

        userKeywordsReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                task.result.children.forEach { dataSnapshot ->
                    dataSnapshot.key?.let { keyword ->
                        subscribeTo(keyword) { e ->
                            // 재구독에 실패한 항목은 제거한다.
                            dataSnapshot.ref.removeValue()
                            keywordReference.child(keyword).setValue(ServerValue.increment(-1))

                            Log.e(TAG, "Failed to subscribe $keyword: ${e.stackTrace}")
                        }
                    }
                }
            } else {
                throw task.exception!!
            }
        }
    }

    override fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {
        // 한글로된 토픽을 못쓰기 때문에 변환을 해준다.
        Firebase.messaging.subscribeToTopic(topic.asEnglish).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                onFailure(task.exception!!)
            }
        }
    }

    override fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {
        // 한글로된 토픽을 못쓰기 때문에 변환을 해준다.
        Firebase.messaging.unsubscribeFromTopic(topic.asEnglish).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                onFailure(task.exception!!)
            }
        }
    }

    companion object {
        private const val TAG = "FirebaseRepositoryImpl"
    }
}