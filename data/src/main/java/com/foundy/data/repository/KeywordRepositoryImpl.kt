package com.foundy.data.repository

import android.util.Log
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.model.Keyword
import com.foundy.domain.repository.KeywordRepository
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class KeywordRepositoryImpl : KeywordRepository {

    private val keywordsReference = Firebase.database.reference.child("keywords")

    private val userKeywordsReference: DatabaseReference?
        get() {
            val uid = Firebase.auth.uid ?: return null
            return Firebase.database.reference
                .child("users")
                .child(uid)
                .child("keywords")
        }

    override fun getAll(): Flow<Result<List<Keyword>>> = callbackFlow {
        val reference = userKeywordsReference
        if (reference == null) {
            this@callbackFlow.trySendBlocking(Result.failure(NotSignedInException()))
            return@callbackFlow
        }
        val postListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                this@callbackFlow.trySendBlocking(Result.failure(error.toException()))
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.map { ds ->
                    ds.key?.let { it -> Keyword(it) }
                }
                this@callbackFlow.trySendBlocking(Result.success(items.filterNotNull()))
            }
        }
        reference.addValueEventListener(postListener)
        awaitClose { reference.removeEventListener(postListener) }
    }

    override fun add(keyword: Keyword) {
        val userRef = userKeywordsReference ?: throw NotSignedInException()
        userRef.child(keyword.title)
            .setValue(1)
            .addOnCompleteListener(onCompleteListener)
        keywordsReference.child(keyword.title)
            .setValue(ServerValue.increment(1))
            .addOnCompleteListener(onCompleteListener)
    }

    override fun remove(keyword: Keyword) {
        val userRef = userKeywordsReference ?: throw NotSignedInException()
        userRef.child(keyword.title)
            .removeValue()
            .addOnCompleteListener(onCompleteListener)
        keywordsReference.child(keyword.title)
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