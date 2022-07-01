package com.foundy.test_utils

import android.view.View
import androidx.annotation.VisibleForTesting
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

@VisibleForTesting
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