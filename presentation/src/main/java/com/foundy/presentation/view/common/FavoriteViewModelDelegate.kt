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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AssistedFactory
interface FavoriteViewModelDelegateFactory {
    fun create(
        viewModelScope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main
    ): FavoriteViewModelDelegate
}

class FavoriteViewModelDelegate @AssistedInject constructor(
    readFavoriteListUseCase: ReadFavoriteListUseCase,
    private val addFavoriteNoticeUseCase: AddFavoriteNoticeUseCase,
    private val removeFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase,
    @Assisted private val viewModelScope: CoroutineScope,
    @Assisted private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {

    val favoritesState = readFavoriteListUseCase().map { list ->
        list.map(::createNoticeUiState)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

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
                favoritesState.value.any { it.notice.url == notice.url }
            }
        )
    }
}
