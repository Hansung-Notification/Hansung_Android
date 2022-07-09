package com.foundy.hansungnotification

import android.content.Context
import android.content.pm.ActivityInfo
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.domain.usecase.firebase.IsSignedInUseCase
import com.foundy.domain.usecase.firebase.SubscribeToUseCase
import com.foundy.domain.usecase.firebase.UnsubscribeFromUseCase
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.notice.HasSearchResultUseCase
import com.foundy.domain.usecase.notice.SearchNoticeListUseCase
import com.foundy.hansungnotification.fake.FakeFavoriteRepositoryImpl
import com.foundy.hansungnotification.fake.FakeFirebaseRepositoryImpl
import com.foundy.hansungnotification.fake.FakeKeywordRepositoryImpl
import com.foundy.hansungnotification.fake.FakeNoticeRepositoryImpl
import com.foundy.hansungnotification.utils.RetryTestRule
import com.foundy.presentation.R
import com.foundy.presentation.view.NoticeViewModel
import com.foundy.presentation.view.keyword.KeywordActivity
import com.foundy.presentation.view.keyword.KeywordViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class KeywordActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val retryRule = RetryTestRule()

    private val fakeKeywordRepository = FakeKeywordRepositoryImpl()
    private val fakeFirebaseRepository = FakeFirebaseRepositoryImpl()
    private val fakeNoticeRepository = FakeNoticeRepositoryImpl()
    private val fakeFavoriteRepository = FakeFavoriteRepositoryImpl()

    @BindValue
    val noticeViewModel = NoticeViewModel(
        GetNoticeListUseCase(fakeNoticeRepository),
        ReadFavoriteListUseCase(fakeFavoriteRepository),
        AddFavoriteNoticeUseCase(fakeFavoriteRepository),
        RemoveFavoriteNoticeUseCase(fakeFavoriteRepository),
        SearchNoticeListUseCase(fakeNoticeRepository)
    )

    @BindValue
    val keywordViewModel = KeywordViewModel(
        ReadKeywordListUseCase(fakeKeywordRepository),
        AddKeywordUseCase(fakeKeywordRepository),
        RemoveKeywordUseCase(fakeKeywordRepository),
        SubscribeToUseCase(fakeFirebaseRepository),
        UnsubscribeFromUseCase(fakeFirebaseRepository),
        IsSignedInUseCase(fakeFirebaseRepository),
        HasSearchResultUseCase(fakeNoticeRepository)
    )
    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun showLoginFragment_ifNotSignedIn() = runTest {
        fakeFirebaseRepository.setSignedIn(false)

        launchActivity<KeywordActivity>()

        onView(withId(R.id.loginFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun showKeywordFragment_ifSignedIn() = runTest {
        fakeFirebaseRepository.setSignedIn(true)

        launchActivity<KeywordActivity>()

        onView(withId(R.id.keywordFragment)).check(matches(isDisplayed()))
    }

    @Test
    fun navControllerWorksWithoutCrash_whenRecreateIfLoggedIn() {
        fakeFirebaseRepository.setSignedIn(true)
        val scenario = launchActivity<KeywordActivity>()

        scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.keywordFragment)).check(matches(isDisplayed()))
    }
}