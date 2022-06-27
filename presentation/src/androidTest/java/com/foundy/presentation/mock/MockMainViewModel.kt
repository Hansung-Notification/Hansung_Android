package com.foundy.presentation.mock

import com.foundy.domain.usecase.GetNoticeListUseCase
import com.foundy.domain.usecase.ReadFavoriteListUseCase
import com.foundy.presentation.view.MainViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow

fun mockMainViewModel(
    mockGetNoticeListUseCase: GetNoticeListUseCase? = null,
    mockReadFavoriteListUseCase: ReadFavoriteListUseCase? = null,
) : MainViewModel {
    val getNoticeListUseCase = mockGetNoticeListUseCase ?: mockk()
    if (mockGetNoticeListUseCase == null)
        every { getNoticeListUseCase() } returns emptyFlow()

    val readFavoriteListUseCase = mockReadFavoriteListUseCase ?: mockk()
    if (mockReadFavoriteListUseCase == null)
        coEvery { readFavoriteListUseCase() } returns Result.success(emptyList())

    return MainViewModel(
        getNoticeListUseCase,
        readFavoriteListUseCase,
        mockk(relaxed = true),
        mockk(relaxed = true)
    )
}