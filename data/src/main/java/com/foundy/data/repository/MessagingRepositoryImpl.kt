package com.foundy.data.repository

import android.util.Log
import com.foundy.data.constant.FirebaseConstant.USERS
import com.foundy.data.constant.FirebaseConstant.KEYWORDS
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.repository.MessagingRepository
import com.github.kimcore.inko.Inko.Companion.asEnglish
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class MessagingRepositoryImpl : MessagingRepository {

    /**
     * Firebase database에 저장되어 있는 키워드들을 구독한다.
     *
     * 앱을 제거하면 구독이 취소되기 때문에 앱 설치 후 재로그인시 이 함수를 호출해야 한다. 만약 재구독에 실패한다면 해당 키워드는
     * 삭제된다. 만약 이전 항목을 얻어오는 것 자체를 실패한다면 예외를 던진다.
     *
     * 또한 여러 기기에서 하나의 계정을 이용하는 경우 앱 실행시마다 이 함수를 호출해야 한다. 다른 기기에서 키워드를 저장하면
     * 구독은 해당 기기만 수행 되지만 DB는 모든 기기에서 동기화 된다. 이는 사용자에게 혼란을 야기할 수 있다.
     */
    override fun subscribeAllDbKeywords() {
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