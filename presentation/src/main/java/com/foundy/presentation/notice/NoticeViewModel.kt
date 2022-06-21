package com.foundy.presentation.notice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.GetNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeViewModel @Inject constructor(
    private val getNoticeListUseCase: GetNoticeListUseCase,
): ViewModel() {

    private val _noticeList = MutableLiveData<List<Notice>>()
    val noticeList: LiveData<List<Notice>> get() = _noticeList

    init {
        getNoticeList()
    }

    private fun getNoticeList() {
        viewModelScope.launch {
            val result = getNoticeListUseCase()
            _noticeList.postValue(result.getOrNull())
        }
    }
}