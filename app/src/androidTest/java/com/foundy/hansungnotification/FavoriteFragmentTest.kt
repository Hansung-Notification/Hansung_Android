package com.foundy.hansungnotification

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.hansungnotification.factory.NoticeFactory
import com.foundy.hansungnotification.factory.NoticeType
import com.foundy.hansungnotification.fake.FakeNoticeItemUiStateCreatorFactory
import com.foundy.hansungnotification.fake.FakeFavoriteRepositoryImpl
import com.foundy.hansungnotification.utils.RetryTestRule
import com.foundy.hansungnotification.utils.launchFragmentInHiltContainer
import com.foundy.hansungnotification.utils.waitForView
import com.foundy.hansungnotification.utils.withIndex
import com.foundy.presentation.R
import com.foundy.presentation.view.home.favorite.FavoriteFragment
import com.foundy.presentation.view.home.favorite.FavoriteViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class FavoriteFragmentTest {

    @Rule
    @JvmField
    val retryRule = RetryTestRule()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    private val fakeFavoriteRepository = FakeFavoriteRepositoryImpl()

    private val mockNotices = listOf(
        NoticeFactory.create(NoticeType.NORMAL),
        NoticeFactory.create(NoticeType.NORMAL),
        NoticeFactory.create(NoticeType.NORMAL)
    )

    @BindValue
    val viewModel = FavoriteViewModel(
        ReadFavoriteListUseCase(fakeFavoriteRepository),
        FakeNoticeItemUiStateCreatorFactory(fakeFavoriteRepository)
    )

    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun itemDisappears_whenFavoriteButtonClicked() = runTest {
        launchFragmentInHiltContainer<FavoriteFragment>()

        fakeFavoriteRepository.emit(mockNotices)

        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(mockNotices.size)))

        onView(withIndex(withId(R.id.favButton), 0)).perform(ViewActions.click())

        onView(withId(R.id.recyclerView)).check(matches(hasChildCount(mockNotices.size - 1)))
    }

    @Test
    fun showEmptyFavoriteText_ifThereIsNoFavorite() = runTest {
        launchFragmentInHiltContainer<FavoriteFragment>()

        onView(withId(R.id.emptyText)).check(matches(isDisplayed()))

        val notice = mockNotices.first()
        fakeFavoriteRepository.add(notice)
        waitForView(withId(R.id.emptyText), not(isDisplayed()))

        fakeFavoriteRepository.remove(notice)
        waitForView(withId(R.id.emptyText), isDisplayed())
    }
}