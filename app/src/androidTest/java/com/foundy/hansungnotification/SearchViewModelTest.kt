package com.foundy.hansungnotification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foundy.domain.usecase.notice.SearchNoticeListUseCase
import com.foundy.domain.usecase.query.AddRecentQueryUseCase
import com.foundy.domain.usecase.query.GetRecentQueryListUseCase
import com.foundy.domain.usecase.query.RemoveRecentQueryUseCase
import com.foundy.domain.usecase.query.UpdateRecentQueryUseCase
import com.foundy.hansungnotification.fake.FakeFavoriteViewModelDelegateFactory
import com.foundy.hansungnotification.fake.FakeFavoriteRepositoryImpl
import com.foundy.hansungnotification.fake.FakeNoticeRepositoryImpl
import com.foundy.hansungnotification.fake.FakeQueryRepositoryImpl
import com.foundy.presentation.view.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fakeQueryRepository = FakeQueryRepositoryImpl()
    private val fakeFavoriteRepository = FakeFavoriteRepositoryImpl()
    private val fakeNoticeRepository = FakeNoticeRepositoryImpl()

    private val searchViewModel = SearchViewModel(
        GetRecentQueryListUseCase(fakeQueryRepository),
        AddRecentQueryUseCase(fakeQueryRepository),
        RemoveRecentQueryUseCase(fakeQueryRepository),
        UpdateRecentQueryUseCase(fakeQueryRepository),
        SearchNoticeListUseCase(fakeNoticeRepository),
        FakeFavoriteViewModelDelegateFactory(fakeFavoriteRepository),
        dispatcher = testDispatcher
    )

    @Test
    fun addOrUpdateWorksCorrectly() = runTest {
        val query1 = "장학금"
        val query2 = "학생"

        // Create an empty collector for the StateFlow
        val collectJob = launch(testDispatcher) {
            searchViewModel.recentQueries.collect()
        }
        assertEquals(0, searchViewModel.recentQueries.value.size)

        searchViewModel.addOrUpdateRecent(query1)
        assertEquals(1, searchViewModel.recentQueries.value.size)

        searchViewModel.addOrUpdateRecent(query1)
        assertEquals(1, searchViewModel.recentQueries.value.size)

        searchViewModel.addOrUpdateRecent(query2)
        assertEquals(2, searchViewModel.recentQueries.value.size)

        collectJob.cancel()
    }
}
