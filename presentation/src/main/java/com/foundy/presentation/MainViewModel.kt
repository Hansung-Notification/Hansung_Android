package com.foundy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.GetNoticeListUseCase
import com.foundy.domain.usecase.ReadFavoriteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNoticeListUseCase: GetNoticeListUseCase,
    private val readFavoriteListUseCase: ReadFavoriteListUseCase
) : ViewModel() {

    private val _noticeList = MutableLiveData<List<Notice>>()
    val noticeList: LiveData<List<Notice>> get() = _noticeList

    private val _isNetworkError = MutableLiveData(false)
    val isNetworkError: LiveData<Boolean> get() = _isNetworkError

    private val _favoriteList = MutableLiveData<List<Notice>>(emptyList())
    val favoriteList: LiveData<List<Notice>> get() = _favoriteList

    private val _isLocalDbError = MutableLiveData(false)
    val isLocalDbError: LiveData<Boolean> get() = _isLocalDbError

    init {
        updateNoticeList()
        readFavoriteList()
    }

    fun updateNoticeList() {
        viewModelScope.launch {
            val result = getNoticeListUseCase()
            if (result.isSuccess) {
                _noticeList.postValue(result.getOrNull())
                _isNetworkError.postValue(false)
            } else {
                _isNetworkError.postValue(true)
            }
        }
    }

    private fun readFavoriteList() {
        val result = readFavoriteListUseCase()
        if (result.isSuccess) {
            _favoriteList.value = result.getOrNull()
            _isLocalDbError.value = false
        } else {
            _isLocalDbError.value = true
        }
    }
}