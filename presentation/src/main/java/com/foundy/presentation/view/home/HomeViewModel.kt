package com.foundy.presentation.view.home

import androidx.lifecycle.*
import com.foundy.domain.exception.NotSignedInException
import com.foundy.domain.usecase.messaging.SubscribeAllDbKeywordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val subscribeAllDbKeywordsUseCase: SubscribeAllDbKeywordsUseCase,
) : ViewModel() {

    fun subscribeAllDbKeywords() {
        try {
            subscribeAllDbKeywordsUseCase()
        } catch (e: NotSignedInException) {
        }
    }
}