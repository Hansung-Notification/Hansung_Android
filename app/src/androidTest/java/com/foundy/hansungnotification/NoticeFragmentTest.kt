package com.foundy.hansungnotification

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.foundy.domain.usecase.notice.GetNoticeListUseCase
import com.foundy.hansungnotification.factory.NoticeFactory
import com.foundy.hansungnotification.factory.NoticeType
import com.foundy.hansungnotification.fake.FakeNoticeItemUiStateCreatorFactory
import com.foundy.hansungnotification.fake.FakeFavoriteRepositoryImpl
import com.foundy.hansungnotification.fake.FakeNoticeRepositoryImpl
import com.foundy.hansungnotification.utils.launchFragmentInHiltContainer
import com.foundy.hansungnotification.utils.waitForView
import com.foundy.presentation.R
import com.foundy.presentation.view.home.notice.NoticeFragment
import com.foundy.presentation.view.home.notice.NoticeViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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

    private val fakeNoticeRepository = FakeNoticeRepositoryImpl()
    private val fakeFavoriteRepository = FakeFavoriteRepositoryImpl()

    private val mockNotices = listOf(
        NoticeFactory.create(NoticeType.HEADER),
        NoticeFactory.create(NoticeType.NORMAL),
        NoticeFactory.create(NoticeType.NORMAL)
    )

    @BindValue
    val viewModel = NoticeViewModel(
        GetNoticeListUseCase(fakeNoticeRepository),
        FakeNoticeItemUiStateCreatorFactory(fakeFavoriteRepository)
    )

    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun loadsNoticesCorrectly() = runTest {
        launchFragmentInHiltContainer<NoticeFragment>()

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