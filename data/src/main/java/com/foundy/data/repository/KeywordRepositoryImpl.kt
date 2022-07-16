package com.foundy.data.repository

import android.util.Log
import com.foundy.data.reference.DatabaseReferenceGetter
import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Named
import com.foundy.domain.exception.NotSignedInException

class KeywordRepositoryImpl @Inject constructor(
    @Named("keywords") private val keywordsReferenceGetter: DatabaseReferenceGetter,
    @Named("userKeywords") private val userKeywordsReferenceGetter: DatabaseReferenceGetter
) : KeywordRepository {

    /**
     * 유저의 키워드 목록 흐름을 반환한다.
     *
     * 반드시 이 함수를 호출하기 전에 로그인이 되어있어야 한다. 그렇지 않으면 [NotSignedInException]을 던진다.
     */
    override fun getAll(): Flow<Result<List<Keyword>>> = callbackFlow {
        val reference = userKeywordsReferenceGetter()
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.map { ds ->
                    ds.key?.let { Keyword(it) }
                }
                this@callbackFlow.trySendBlocking(Result.success(items.filterNotNull()))
            }
        }
        reference.addValueEventListener(postListener)
        awaitClose { reference.removeEventListener(postListener) }
    }

    /**
     * 키워드를 추가한다.
     *
     * 반드시 이 함수를 호출하기 전에 로그인이 되어있어야 한다. 그렇지 않으면 [NotSignedInException]을 던진다.
     */
    override fun add(keyword: Keyword) {
        val userRef = userKeywordsReferenceGetter()
        userRef.child(keyword.title)
            .setValue(1)
            .addOnCompleteListener(onCompleteListener)
        keywordsReferenceGetter().child(keyword.title)
            .setValue(ServerValue.increment(1))
            .addOnCompleteListener(onCompleteListener)
    }

    /**
     * 키워드를 삭제한다.
     *
     * 반드시 이 함수를 호출하기 전에 로그인이 되어있어야 한다. 그렇지 않으면 [NotSignedInException]을 던진다.
     */
    override fun remove(keyword: Keyword) {
        val userRef = userKeywordsReferenceGetter()
        userRef.child(keyword.title)
            .removeValue()
            .addOnCompleteListener(onCompleteListener)
        keywordsReferenceGetter().child(keyword.title)
            .setValue(ServerValue.increment(-1))
            .addOnCompleteListener(onCompleteListener)
    }

    private val onCompleteListener = OnCompleteListener<Void> {
        if (!it.isSuccessful) {
            Log.e(TAG, it.exception!!.stackTraceToString())
        }
    }

    companion object {
        private const val TAG = "KeywordRepositoryImpl"
    }
}