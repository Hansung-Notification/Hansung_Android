package com.foundy.hansungnotification

import android.content.Context
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.foundy.domain.usecase.favorite.AddFavoriteNoticeUseCase
import com.foundy.domain.usecase.favorite.ReadFavoriteListUseCase
import com.foundy.domain.usecase.favorite.RemoveFavoriteNoticeUseCase
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.domain.usecase.notice.SearchNoticeListUseCase
import com.foundy.hansungnotification.factory.NoticeFactory
import com.foundy.hansungnotification.factory.NoticeType
import com.foundy.hansungnotification.fake.FakeFavoriteRepositoryImpl
import com.foundy.hansungnotification.fake.FakeNoticeRepositoryImpl
import com.foundy.hansungnotification.utils.waitForView
import com.foundy.presentation.R
import com.foundy.presentation.view.home.HomeViewModel
import com.foundy.presentation.view.home.notice.NoticeFragment
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class NoticeFragmentTest {

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
    val viewModel = HomeViewModel(
        GetNoticeListUseCase(fakeNoticeRepository),
        ReadFavoriteListUseCase(fakeFavoriteRepository),
        AddFavoriteNoticeUseCase(fakeFavoriteRepository),
        RemoveFavoriteNoticeUseCase(fakeFavoriteRepository),
        SearchNoticeListUseCase(fakeNoticeRepository)
    )

    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext

        with(mockk<ViewModelProvider.Factory>()) {
            every { create(HomeViewModel::class.java) } answers { viewModel }
            every { fragmentFactory.instantiate(any(), any()) } answers {
                NoticeFragment { this@with }
            }
        }
    }

    @Test
    fun loadsNoticesCorrectly() = runTest {
        launchFragmentInContainer<NoticeFragment>(
            factory = fragmentFactory,
            themeResId = R.style.Theme_HansungNotification
        )

        fakeNoticeRepository.setFakeList(mockNotices)
        fakeNoticeRepository.emitFake()

        waitForView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(mockNotices.size, recyclerView.adapter?.itemCount)
        }
    }
}