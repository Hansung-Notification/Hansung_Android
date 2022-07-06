package com.foundy.hansungnotification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.foundy.domain.usecase.query.AddRecentQueryUseCase
import com.foundy.domain.usecase.query.GetRecentQueryListUseCase
import com.foundy.domain.usecase.query.RemoveRecentQueryUseCase
import com.foundy.domain.usecase.query.UpdateRecentQueryUseCase
import com.foundy.hansungnotification.fake.FakeQueryRepositoryImpl
import com.foundy.hansungnotification.utils.observeForTesting
import com.foundy.presentation.view.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
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

        searchViewModel.recentQueries.observeForTesting {
            runBlocking {
                searchViewModel.addOrUpdateRecent(query1)
                delay(50)
                assertEquals(1, searchViewModel.recentQueries.value?.size)

                searchViewModel.addOrUpdateRecent(query1)
                delay(50)
                assertEquals(1, searchViewModel.recentQueries.value?.size)

                searchViewModel.addOrUpdateRecent(query2)
                delay(50)
                assertEquals(2, searchViewModel.recentQueries.value?.size)
            }
        }
    }
}
