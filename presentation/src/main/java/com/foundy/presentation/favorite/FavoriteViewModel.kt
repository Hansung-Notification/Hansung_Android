package com.foundy.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.ReadFavoriteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val readFavoriteListUseCase: ReadFavoriteListUseCase
) : ViewModel() {

    private val _favoriteList = MutableLiveData<List<Notice>>(emptyList())
    val favoriteList: LiveData<List<Notice>> get() = _favoriteList

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError

    init {
        readFavoriteList()
    }

    private fun readFavoriteList() {
        val result = readFavoriteListUseCase()
        if (result.isSuccess) {
            _favoriteList.value = result.getOrNull()
            _isError.value = false
        } else {
            _isError.value = true
        }
    }
}