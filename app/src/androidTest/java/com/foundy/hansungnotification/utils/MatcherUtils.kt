package com.foundy.hansungnotification.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.util.TreeIterables
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?> {
    return object : TypeSafeMatcher<View?>() {
        var currentIndex = 0

        override fun describeTo(description: Description) {
            description.appendText("with index: ")
            description.appendValue(index)
            matcher.describeTo(description)
        }

        override fun matchesSafely(view: View?): Boolean {
            return matcher.matches(view) && currentIndex++ == index
        }
    }
}

private fun searchForView(viewMatcher: Matcher<View>): ViewAction {
    return object : ViewAction {
        override fun getConstraints() = isRoot()
        override fun getDescription() = "search for view with $viewMatcher in the root view"

        override fun perform(uiController: UiController, view: View) {
            TreeIterables.breadthFirstViewTraversal(view).forEach {
                if (viewMatcher.matches(it)) {
                    return
                }
            }

            throw NoMatchingViewException.Builder()
                .withRootView(view)
                .withViewMatcher(viewMatcher)
                .build()
        }
    }
}

/**
 * [waitForView] tries to find a view with given [viewMatchers]. If found, it returns the
 * [ViewInteraction] for given [viewMatchers]. If not found, it waits for given [wait]
 * before attempting to find the view again. It reties for given number of [retries].
 *
 * Adaptation of the [StackOverflow post by manbradcalf](https://stackoverflow.com/a/56499223/2410641)
 */
fun waitForView(
    vararg viewMatchers: Matcher<View>,
    retries: Int = 5,
    wait: Long = 1000L,
): ViewInteraction {
    require(retries > 0 && wait > 0)
    val viewMatcher = allOf(*viewMatchers)
    for (i in 0 until retries) {
        try {
            onView(isRoot()).perform(searchForView(viewMatcher))
            break
        } catch (e: NoMatchingViewException) {
            if (i == retries) {
                throw e
            }

            Thread.sleep(wait)
        }
    }

    return onView(viewMatcher)
}