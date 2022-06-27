package com.foundy.presentation

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.foundy.domain.usecase.GetNoticeListUseCase
import com.foundy.presentation.factory.NoticeFactory
import com.foundy.presentation.factory.NoticeType
import com.foundy.presentation.mock.mockMainViewModel
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.view.notice.NoticeFragment
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoticeFragmentTest {

    private val fragmentFactory: FragmentFactory = mockk()

    private val mockNotices = listOf(
        NoticeFactory.create(NoticeType.HEADER),
        NoticeFactory.create(NoticeType.NORMAL),
        NoticeFactory.create(NoticeType.NORMAL)
    )
    private val mockNoticeFlow = flowOf(PagingData.from(mockNotices))

    private val viewModel: MainViewModel = mockMainViewModel(
        mockk<GetNoticeListUseCase>().also { every { it() } returns mockNoticeFlow }
    )

    @Before
    fun setUp() {
        with(mockk<ViewModelProvider.Factory>()) {
            every { create(MainViewModel::class.java) } answers { viewModel }
            every { fragmentFactory.instantiate(any(), any()) } answers {
                NoticeFragment { this@with }
            }
        }
    }

    @Test
    fun loadsNoticesCorrectly() {
        launchFragmentInContainer<NoticeFragment>(factory = fragmentFactory)

        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(mockNotices.size, recyclerView.adapter?.itemCount)
        }
    }
}