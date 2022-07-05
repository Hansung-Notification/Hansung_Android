package com.foundy.presentation.view

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.domain.usecase.notice.SearchNoticeListUseCase
import com.foundy.presentation.model.NoticeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    private val readFavoriteListUseCase: ReadFavoriteListUseCase,
    private val addFavoriteNoticeUseCase: AddFavoriteNoticeUseCase,
    private val removeFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase,
    private val searchNoticeListUseCase: SearchNoticeListUseCase
) : ViewModel() {

    private val _favoriteList = MutableLiveData<List<NoticeUiState>>(emptyList())
    val favoriteList: LiveData<List<NoticeUiState>> get() = _favoriteList

    val noticeFlow = getNoticeListUseCase().cachedIn(viewModelScope).map {
        it.map(::createNoticeUiState)
    }

    init {
        initFavoriteList()
    }

    fun search(query: String): Flow<PagingData<NoticeUiState>> {
        return searchNoticeListUseCase(query).cachedIn(viewModelScope).map {
            it.map(::createNoticeUiState)
        }
    }

    private fun createNoticeUiState(notice: Notice): NoticeUiState {
        return NoticeUiState(
            notice,
            onClickFavorite = { if (it) addFavoriteItem(notice) else removeFavoriteItem(notice) },
            isFavorite = {
                favoriteList.value?.let { list ->
                    list.firstOrNull { it.notice.url == notice.url } != null
                } ?: false
            }
        )
    }

    private fun initFavoriteList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                readFavoriteListUseCase().collect { list ->
                    _favoriteList.postValue(list.map(::createNoticeUiState))
                }
            }
        }
    }

    private fun addFavoriteItem(notice: Notice) {
        viewModelScope.launch {
            addFavoriteNoticeUseCase(notice)
        }
    }

    private fun removeFavoriteItem(notice: Notice) {
        viewModelScope.launch {
            removeFavoriteNoticeUseCase(notice)
        }
    }
}