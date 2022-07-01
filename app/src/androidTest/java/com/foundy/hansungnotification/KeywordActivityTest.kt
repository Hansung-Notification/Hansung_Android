package com.foundy.hansungnotification

import android.content.Context
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.keyword.AddKeywordUseCase
import com.foundy.domain.usecase.keyword.ReadKeywordListUseCase
import com.foundy.domain.usecase.keyword.RemoveKeywordUseCase
import com.foundy.hansungnotification.fake.FakeKeywordRepositoryImpl
import com.foundy.presentation.R
import com.foundy.presentation.view.keyword.KeywordActivity
import com.foundy.presentation.view.keyword.KeywordViewModel
import com.foundy.test_utils.withIndex
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
class KeywordActivityTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val scenario = ActivityScenarioRule(KeywordActivity::class.java)

    private val fakeRepository = FakeKeywordRepositoryImpl()

    @BindValue
    val keywordViewModel = KeywordViewModel(
        ReadKeywordListUseCase(fakeRepository),
        AddKeywordUseCase(fakeRepository),
        RemoveKeywordUseCase(fakeRepository)
    )

    lateinit var context: Context

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
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
        val list = (0..9).map { Keyword(it.toString()) }
        fakeRepository.setFakeList(list)
        fakeRepository.emitFake()

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
        val list = (0..9).map { Keyword(it.toString()) }
        fakeRepository.setFakeList(list)
        fakeRepository.emitFake()

        onView(withId(R.id.textInputLayout)).check(matches(isNotEnabled()))

        onView(withIndex(withId(R.id.delete_button), 0)).perform(click())

        onView(withId(R.id.textInputLayout)).check(matches(isEnabled()))
    }

    @Test
    fun showWarningMessage_ifSendAlreadyExistsKeyword() = runTest {
        val keyword = Keyword("hello")
        fakeRepository.setFakeList(listOf(keyword))
        fakeRepository.emitFake()

        onView(withId(R.id.title)).check(matches(withText(keyword.title)))

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
