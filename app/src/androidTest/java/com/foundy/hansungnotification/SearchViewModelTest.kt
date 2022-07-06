package com.foundy.hansungnotification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.foundy.domain.usecase.query.AddRecentQueryUseCase
import com.foundy.domain.usecase.query.GetRecentQueryListUseCase
import com.foundy.domain.usecase.query.RemoveRecentQueryUseCase
import com.foundy.domain.usecase.query.UpdateRecentQueryUseCase
import com.foundy.hansungnotification.fake.FakeQueryRepositoryImpl
import com.foundy.hansungnotification.utils.getOrAwaitValue
import com.foundy.presentation.view.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SearchViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val fakeRepository = FakeQueryRepositoryImpl()

    private val searchViewModel = SearchViewModel(
        GetRecentQueryListUseCase(fakeRepository),
        AddRecentQueryUseCase(fakeRepository),
        RemoveRecentQueryUseCase(fakeRepository),
        UpdateRecentQueryUseCase(fakeRepository),
        dispatcher = testDispatcher
    )

    @Test
    fun addOrUpdateWorksCorrectly() = runTest {
        val query1 = "장학금"
        val query2 = "학생"

        // 구독을 해야지 값이 변경된다.
        searchViewModel.recentQueries.observeForever {}
        searchViewModel.addOrUpdateRecent(query1)
        assertEquals(1, searchViewModel.recentQueries.getOrAwaitValue().size)

        searchViewModel.addOrUpdateRecent(query1)
        assertEquals(1, searchViewModel.recentQueries.getOrAwaitValue().size)

        searchViewModel.addOrUpdateRecent(query2)
        assertEquals(1, searchViewModel.recentQueries.getOrAwaitValue().size)
    }
}
