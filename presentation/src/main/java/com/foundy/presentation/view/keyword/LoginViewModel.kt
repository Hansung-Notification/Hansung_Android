package com.foundy.presentation.view.keyword

import androidx.lifecycle.ViewModel
import com.foundy.domain.usecase.firebase.SignInWithUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithUseCase: SignInWithUseCase
) : ViewModel() {

    fun signInWith(idToken: String) = signInWithUseCase(idToken)
}