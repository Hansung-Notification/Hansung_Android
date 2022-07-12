package com.foundy.presentation.view.common

import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.presentation.model.NoticeUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AssistedFactory
interface FavoriteViewModelDelegateFactory {
    fun create(
        viewModelScope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main
    ): FavoriteViewModelDelegate
}

class FavoriteViewModelDelegate @AssistedInject constructor(
    private val readFavoriteListUseCase: ReadFavoriteListUseCase,
    private val addFavoriteNoticeUseCase: AddFavoriteNoticeUseCase,
    private val removeFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase,
    @Assisted private val viewModelScope: CoroutineScope,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    private val _favoritesState = MutableStateFlow<List<NoticeUiState>>(emptyList())
    val favoritesState: StateFlow<List<NoticeUiState>> = _favoritesState

    init {
        initFavoriteList()
    }

    /**
     * Favorite에 대한 상태를 가진 [NoticeUiState]를 [notice]로부터 생성한다.
     */
    fun createNoticeUiState(notice: Notice): NoticeUiState {
        return NoticeUiState(
            notice,
            onClickFavorite = { isFavorite ->
                viewModelScope.launch(dispatcher) {
                    if (isFavorite) {
                        addFavoriteNoticeUseCase(notice)
                    } else {
                        removeFavoriteNoticeUseCase(notice)
                    }
                }
            },
            isFavorite = {
                favoritesState.value.let { list ->
                    list.firstOrNull { it.notice.url == notice.url } != null
                }
            }
        )
    }

    private fun initFavoriteList() {
        viewModelScope.launch(dispatcher) {
            readFavoriteListUseCase().collect { list ->
                _favoritesState.emit(list.map { createNoticeUiState(it) })
            }
        }
    }
}
