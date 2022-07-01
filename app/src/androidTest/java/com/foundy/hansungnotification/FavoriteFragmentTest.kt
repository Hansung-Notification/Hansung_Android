package com.foundy.hansungnotification

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.foundy.domain.testutils.NoticeFactory
import com.foundy.domain.testutils.NoticeType
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.hansungnotification.fake.FakeFavoriteRepositoryImpl
import com.foundy.hansungnotification.fake.FakeNoticeRepositoryImpl
import com.foundy.presentation.R
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.view.favorite.FavoriteFragment
import com.foundy.test_utils.withIndex
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class FavoriteFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    private val fragmentFactory: FragmentFactory = mockk()

    private val fakeNoticeRepository = FakeNoticeRepositoryImpl()
    private val fakeFavoriteRepository = FakeFavoriteRepositoryImpl()

    private val mockNotices = listOf(
        NoticeFactory.create(NoticeType.HEADER),
        NoticeFactory.create(NoticeType.NORMAL),
        NoticeFactory.create(NoticeType.NORMAL)
    )

    @BindValue
    val viewModel = MainViewModel(
        GetNoticeListUseCase(fakeNoticeRepository),
        ReadFavoriteListUseCase(fakeFavoriteRepository),
        AddFavoriteNoticeUseCase(fakeFavoriteRepository),
        RemoveFavoriteNoticeUseCase(fakeFavoriteRepository)
    )

    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext

        with(mockk<ViewModelProvider.Factory>()) {
            every { create(MainViewModel::class.java) } answers { viewModel }
            every { fragmentFactory.instantiate(any(), any()) } answers {
                FavoriteFragment { this@with }
            }
        }
    }

    @Test
    fun itemDisappears_whenFavoriteButtonClicked(): Unit = runBlocking{
        launchFragmentInContainer<FavoriteFragment>(factory = fragmentFactory)

        fakeFavoriteRepository.setFakeList(mockNotices)
        fakeFavoriteRepository.emitFake()

        onView(ViewMatchers.withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(mockNotices.size, recyclerView.adapter?.itemCount)
        }

        onView(withIndex(ViewMatchers.withId(R.id.favButton), 0))
            .perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.recyclerView)).check { view, noViewFoundException ->
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