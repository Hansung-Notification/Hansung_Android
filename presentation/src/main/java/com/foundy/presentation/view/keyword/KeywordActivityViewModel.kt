package com.foundy.presentation.view.keyword

import androidx.lifecycle.*
import com.foundy.domain.usecase.auth.IsSignedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KeywordActivityViewModel @Inject constructor(
    private val isSignedInUseCase: IsSignedInUseCase
) : ViewModel() {

    fun isSignedIn() = isSignedInUseCase()
}