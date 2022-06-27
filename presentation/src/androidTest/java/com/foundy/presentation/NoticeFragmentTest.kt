package com.foundy.presentation

import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.foundy.presentation.factory.NoticeFactory
import com.foundy.presentation.factory.NoticeType
import com.foundy.presentation.view.MainViewModel
import com.foundy.presentation.view.notice.NoticeFragment
import com.foundy.presentation.view.notice.NoticeUiState
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

    private val mockNoticeUiStates = listOf(
        NoticeFactory.create(NoticeType.HEADER),
        NoticeFactory.create(NoticeType.NORMAL),
        NoticeFactory.create(NoticeType.NORMAL)
    ).map {
        NoticeUiState(
            it,
            onClickFavorite = { },
            isFavorite = { true }
        )
    }
    private val mockNoticeFlow = flowOf(PagingData.from(mockNoticeUiStates))

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true)
        val viewModelFactory: ViewModelProvider.Factory = mockk()

        every { viewModelFactory.create(MainViewModel::class.java) } answers { viewModel }
        every { fragmentFactory.instantiate(any(), any()) } answers {
            NoticeFragment { viewModelFactory }
        }
    }

    @Test
    fun loadsNoticesCorrectly() {
        every { viewModel.noticeFlow } returns mockNoticeFlow
        launchFragmentInContainer<NoticeFragment>(factory = fragmentFactory)

        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(mockNoticeUiStates.size, recyclerView.adapter?.itemCount)
        }
    }
}