package com.foundy.hansungnotification

import android.content.Context
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
import com.foundy.hansungnotification.fake.FakeFavoriteRepositoryImpl
import com.foundy.hansungnotification.fake.FakeFirebaseRepositoryImpl
import com.foundy.hansungnotification.fake.FakeKeywordRepositoryImpl
import com.foundy.hansungnotification.fake.FakeNoticeRepositoryImpl
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.view.keyword.KeywordViewModel
import com.foundy.presentation.R
import com.foundy.presentation.view.keyword.KeywordActivity
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

    private val fakeKeywordRepository = FakeKeywordRepositoryImpl()
    private val fakeFirebaseRepository = FakeFirebaseRepositoryImpl()
    private val fakeNoticeRepository = FakeNoticeRepositoryImpl()
    private val fakeFavoriteRepository = FakeFavoriteRepositoryImpl()

    @BindValue
    val mainViewModel = MainViewModel(
        GetNoticeListUseCase(fakeNoticeRepository),
        ReadFavoriteListUseCase(fakeFavoriteRepository),
        AddFavoriteNoticeUseCase(fakeFavoriteRepository),
        RemoveFavoriteNoticeUseCase(fakeFavoriteRepository)
    )

    @BindValue
    val keywordViewModel = KeywordViewModel(
        ReadKeywordListUseCase(fakeKeywordRepository),
        AddKeywordUseCase(fakeKeywordRepository),
        RemoveKeywordUseCase(fakeKeywordRepository),
        SubscribeToUseCase(fakeFirebaseRepository),
        UnsubscribeFromUseCase(fakeFirebaseRepository),
        IsSignedInUseCase(fakeFirebaseRepository)
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
}