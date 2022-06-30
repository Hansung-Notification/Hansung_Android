package com.foundy.presentation.mock

import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.presentation.view.MainViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow

fun mockMainViewModel(
    mockGetNoticeListUseCase: GetNoticeListUseCase? = null,
    mockReadFavoriteListUseCase: ReadFavoriteListUseCase? = null,
    mockRemoveFavoriteNoticeUseCase: RemoveFavoriteNoticeUseCase? = null,
) : MainViewModel {
    val getNoticeListUseCase = mockGetNoticeListUseCase ?: mockk()
    if (mockGetNoticeListUseCase == null) {
        every { getNoticeListUseCase() } returns emptyFlow()
    }
    val readFavoriteListUseCase = mockReadFavoriteListUseCase ?: mockk()
    if (mockReadFavoriteListUseCase == null) {
        coEvery { readFavoriteListUseCase() } returns emptyFlow()
    }
    val removeFavoriteNoticeUseCase = mockRemoveFavoriteNoticeUseCase ?: mockk()
    if (mockRemoveFavoriteNoticeUseCase == null) {
        coEvery { removeFavoriteNoticeUseCase(any()) } answers { }
    }
    return MainViewModel(
        getNoticeListUseCase,
        readFavoriteListUseCase,
        mockk(relaxed = true),
        removeFavoriteNoticeUseCase
    )
}