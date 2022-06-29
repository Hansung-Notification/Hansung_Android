package com.foundy.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.GetNoticeListUseCase
import com.foundy.domain.usecase.ReadFavoriteListUseCase
import com.foundy.domain.usecase.RemoveFavoriteNoticeUseCase
import com.foundy.presentation.view.notice.NoticeUiState
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getNoticeListUseCase: GetNoticeListUseCase,
    private val readFavoriteListUseCase: ReadFavoriteListUseCase,
    private val addFavoriteNoticeUseCase: AddFavoriteNoticeUseCase,
    private val removeFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase
) : ViewModel() {

    private val auth = Firebase.auth

    private val _favoriteList = mutableListOf<NoticeUiState>()
    val favoriteList: List<NoticeUiState> get() = _favoriteList

    val noticeFlow = getNoticeListUseCase().cachedIn(viewModelScope).map {
        it.map(::createNoticeUiState)
    }

    init {
        readFavoriteList()
    }

    val isSigned get() = auth.currentUser != null

    fun signInAnonymously(): Task<AuthResult> {
        return auth.signInAnonymously()
    }

    private fun createNoticeUiState(notice: Notice): NoticeUiState {
        return NoticeUiState(
            notice,
            onClickFavorite = { if (it) addFavoriteItem(notice) else removeFavoriteItem(notice) },
            isFavorite = { isFavorite(notice) }
        )
    }

    private fun readFavoriteList() {
        viewModelScope.launch {
            val result = readFavoriteListUseCase()
            if (result.isSuccess) {
                result.getOrNull()?.let { notices ->
                    val states = notices.map(::createNoticeUiState)
                    _favoriteList.addAll(states)
                }
            }
        }
    }

    private fun addFavoriteItem(notice: Notice) {
        _favoriteList.add(createNoticeUiState(notice))
        viewModelScope.launch {
            addFavoriteNoticeUseCase(notice)
        }
    }

    private fun removeFavoriteItem(notice: Notice) {
        _favoriteList.removeAll { it.notice == notice }
        viewModelScope.launch {
            removeFavoriteNoticeUseCase(notice)
        }
    }

    private fun isFavorite(notice: Notice): Boolean {
        return _favoriteList.firstOrNull { it.notice.url == notice.url } != null
    }
}