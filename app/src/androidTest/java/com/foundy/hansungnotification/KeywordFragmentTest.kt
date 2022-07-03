package com.foundy.hansungnotification

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.firebase.IsSignedInUseCase
import com.foundy.domain.usecase.firebase.SubscribeToUseCase
import com.foundy.domain.usecase.firebase.UnsubscribeFromUseCase
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.foundy.hansungnotification.fake.FakeFirebaseRepositoryImpl
import com.foundy.hansungnotification.fake.FakeKeywordRepositoryImpl
import com.foundy.hansungnotification.utils.waitForView
import com.foundy.hansungnotification.utils.withIndex
import com.foundy.presentation.R
import com.foundy.presentation.view.keyword.KeywordActivity
import com.foundy.presentation.view.keyword.KeywordFragment
import com.foundy.presentation.view.keyword.KeywordViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class KeywordFragmentTest {

    private val fragmentFactory: FragmentFactory = mockk()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val scenario = ActivityScenarioRule(KeywordActivity::class.java)

    private val fakeKeywordRepository = FakeKeywordRepositoryImpl()
    private val fakeFirebaseRepository = FakeFirebaseRepositoryImpl()

    @BindValue
    val keywordViewModel = KeywordViewModel(
        ReadKeywordListUseCase(fakeKeywordRepository),
        AddKeywordUseCase(fakeKeywordRepository),
        RemoveKeywordUseCase(fakeKeywordRepository),
        SubscribeToUseCase(fakeFirebaseRepository),
        UnsubscribeFromUseCase(fakeFirebaseRepository),
        IsSignedInUseCase(fakeFirebaseRepository)
    )

    private lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext

        with(mockk<ViewModelProvider.Factory>()) {
            every { create(keywordViewModel::class.java) } answers { keywordViewModel }
            every { fragmentFactory.instantiate(any(), any()) } answers {
                KeywordFragment { this@with }
            }
        }
    }

    @Test
    fun textViewIsEmpty_afterAddedKeyword() {
        inputTextToTextInputEditText("some text")
        pressSendKeyboardButton()

        onView(withId(R.id.textInput)).check { view, noViewFoundException ->
            if (noViewFoundException != null)
                throw noViewFoundException

            val textInput = view as TextInputEditText
            assertTrue(textInput.text!!.isEmpty())
        }
    }

    @Test
    fun cannotAddKeyword_ifTextLengthIsLowerThanTwo() {
        val text = "t"
        inputTextToTextInputEditText(text)
        pressSendKeyboardButton()

        onView(withId(R.id.textInput)).check { view, noViewFoundException ->
            if (noViewFoundException != null)
                throw noViewFoundException

            val textInput = view as TextInputEditText
            assertEquals(textInput.text!!.toString(), text)
        }

        assertSnackBarHasText(R.string.keyword_min_length_warning)
    }

    @Test
    fun disableInputText_ifKeywordListIsFull() = runTest {
        val list = (0 until KeywordFragment.MAX_KEYWORD_COUNT).map { Keyword(it.toString()) }
        fakeKeywordRepository.setFakeList(list)

        onView(withId(R.id.recyclerView)).check { view, noViewFoundException ->
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            val recyclerView = view as RecyclerView
            assertEquals(recyclerView.adapter?.itemCount, list.size)
        }

        onView(withId(R.id.textInputLayout)).check(matches(isNotEnabled()))
        onView(withId(R.id.textInput)).check(
            matches(
                withHint(
                    context.getString(
                        R.string.keyword_max_hint,
                        list.size
                    )
                )
            )
        )
    }

    @Test
    fun enableInputText_afterRemoveKeywordWhenListIsFull() = runTest {
        val list = (0 until KeywordFragment.MAX_KEYWORD_COUNT).map { Keyword(it.toString()) }
        fakeKeywordRepository.setFakeList(list)

        waitForView(withId(R.id.textInputLayout), isNotEnabled())

        onView(withIndex(withId(R.id.delete_button), 0)).perform(click())

        waitForView(withId(R.id.textInputLayout), isEnabled())
    }

    @Test
    fun showWarningMessage_ifSendAlreadyExistsKeyword() = runTest {
        val keyword = Keyword("hello")
        fakeKeywordRepository.setFakeList(listOf(keyword))

        waitForView(withId(R.id.title), withText(keyword.title))

        inputTextToTextInputEditText(keyword.title)
        pressSendKeyboardButton()

        assertSnackBarHasText(R.string.already_exists_keyword)
    }

    private fun inputTextToTextInputEditText(text: String) {
        onView(withId(R.id.textInput)).perform(typeText(text))
    }

    private fun pressSendKeyboardButton() {
        onView(withId(R.id.textInput)).perform(pressImeActionButton())
    }

    private fun assertSnackBarHasText(@StringRes resourceId: Int) {
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(
            matches(withText(resourceId))
        )
    }
}
