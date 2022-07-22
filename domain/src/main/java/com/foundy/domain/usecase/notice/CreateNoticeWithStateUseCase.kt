package com.foundy.domain.usecase.notice

import com.foundy.domain.model.Notice
import com.foundy.domain.model.NoticeWithState
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.IsFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateNoticeWithStateUseCase @Inject constructor(
    private val addFavoriteNoticeUseCase: AddFavoriteNoticeUseCase,
    private val removeFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase,
    private val isFavoriteNoticeUseCase: IsFavoriteNoticeUseCase,
) {

    /**
     * Favorite 상태를 가진 [NoticeWithState]를 [Notice]로부터 생성한다.
     */
    operator fun invoke(
        notice: Notice,
        viewModelScope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.Main
    ): NoticeWithState {
        return NoticeWithState(
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
            isFavorite = { isFavoriteNoticeUseCase(notice) }
        )
    }
}