package com.foundy.presentation

import android.os.Build
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import com.foundy.domain.model.Notice
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.presentation.factory.NoticeFactory
import com.foundy.presentation.factory.NoticeType
import com.foundy.presentation.mock.mockMainViewModel
import com.foundy.presentation.util.withIndex
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.view.favorite.FavoriteFragment
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.P)
class FavoriteFragmentTest {

    private val fragmentFactory: FragmentFactory = mockk()

    private val mockNotices = listOf(
        NoticeFactory.create(NoticeType.HEADER),
        NoticeFactory.create(NoticeType.NORMAL),
        NoticeFactory.create(NoticeType.NORMAL)
    )
    private val mockNoticeFlow = flowOf(PagingData.from(mockNotices))
    private val mockFavoriteFlow = MutableSharedFlow<List<Notice>>()

    private val viewModel: MainViewModel = mockMainViewModel(
        mockk<GetNoticeListUseCase>().also { every { it() } returns mockNoticeFlow },
        mockk<ReadFavoriteListUseCase>().also { coEvery { it() } returns mockFavoriteFlow },
        mockk<RemoveFavoriteNoticeUseCase>().also {
            coEvery { it(any()) } answers {
                runBlocking {
                    // 하나 제거하는 것을 흉내낸다.
                    mockFavoriteFlow.emit(mockNotices.subList(1, mockNotices.size))
                }
            }
        },
    )

    @Before
    fun setUp() {
        with(mockk<ViewModelProvider.Factory>()) {
            every { create(MainViewModel::class.java) } answers { viewModel }
            every { fragmentFactory.instantiate(any(), any()) } answers {
                FavoriteFragment { this@with }
            }
        }
    }

    @Test
    fun itemDisappears_whenFavoriteButtonClicked() {
        launchFragmentInContainer<FavoriteFragment>(factory = fragmentFactory)

        runBlocking {
            mockFavoriteFlow.emit(mockNotices)
        }

        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(mockNotices.size, recyclerView.adapter?.itemCount)
        }

        onView(withIndex(withId(R.id.favButton), 0)).perform(click())

        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            val expectedSize = mockNotices.size - 1
            assertEquals(expectedSize, recyclerView.adapter?.itemCount)
            assertEquals(expectedSize, viewModel.favoriteList.value?.size)
        }
    }
}