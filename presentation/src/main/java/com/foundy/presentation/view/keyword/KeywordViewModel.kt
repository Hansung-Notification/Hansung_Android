package com.foundy.presentation.view.keyword

import androidx.lifecycle.*
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ServerValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.github.kimcore.inko.Inko.Companion.asEnglish
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeywordViewModel @Inject constructor(
    readKeywordListUseCase: ReadKeywordListUseCase,
    private val addKeywordUseCase: AddKeywordUseCase,
    private val removeKeywordUseCase: RemoveKeywordUseCase
) : ViewModel() {

    val keywordList = readKeywordListUseCase().asLiveData()

    private val keywordsReference: DatabaseReference
        get() = Firebase.database.reference.child("keywords")

    fun addKeywordItem(keyword: Keyword) {
        viewModelScope.launch {
            addKeywordUseCase(keyword)
        }
    }

    fun removeKeywordItem(keyword: Keyword) {
        viewModelScope.launch {
            removeKeywordUseCase(keyword)
        }
    }

    fun hasKeyword(keyword: String): Boolean {
        return keywordList.value?.firstOrNull { it.title == keyword } != null
    }

    fun subscribeTo(topic: String, onFailure: (Exception) -> Unit) {
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

    fun unsubscribeFrom(topic: String, onFailure: (Exception) -> Unit) {
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