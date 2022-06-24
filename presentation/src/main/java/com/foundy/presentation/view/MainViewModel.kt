package com.foundy.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.GetNoticeListUseCase
import com.foundy.domain.usecase.ReadFavoriteListUseCase
import com.foundy.domain.usecase.RemoveFavoriteNoticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNoticeListUseCase: GetNoticeListUseCase,
    private val readFavoriteListUseCase: ReadFavoriteListUseCase,
    private val addFavoriteNoticeUseCase: AddFavoriteNoticeUseCase,
    private val removeFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase
) : ViewModel() {

    private val _noticeList = MutableLiveData<List<Notice>>()
    val noticeList: LiveData<List<Notice>> get() = _noticeList

    private val _isNetworkError = MutableLiveData(false)
    val isNetworkError: LiveData<Boolean> get() = _isNetworkError

    private val _favoriteList = mutableListOf<Notice>()
    val favoriteList: List<Notice> get() = _favoriteList

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
        viewModelScope.launch {
            val result = readFavoriteListUseCase()
            if (result.isSuccess) {
                result.getOrNull()?.let { _favoriteList.addAll(it) }
            }
        }
    }

    fun addFavoriteItem(notice: Notice) {
        _favoriteList.add(notice)
        viewModelScope.launch {
            addFavoriteNoticeUseCase(notice)
        }
    }

    fun removeFavoriteItem(notice: Notice) {
        _favoriteList.remove(notice)
        viewModelScope.launch {
            removeFavoriteNoticeUseCase(notice)
        }
    }

    fun isFavorite(notice: Notice): Boolean {
        return _favoriteList.firstOrNull { it.url == notice.url } != null
    }
}