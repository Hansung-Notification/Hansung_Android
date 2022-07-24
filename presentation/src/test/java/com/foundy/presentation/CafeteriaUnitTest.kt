package com.foundy.presentation

import com.foundy.presentation.view.home.cafeteria.getMonToFriTabIndexFrom
import org.joda.time.DateTimeConstants
import org.junit.Assert.assertEquals
import org.junit.Test

class CafeteriaUnitTest {

    @Test
    fun returnsZero_IfPutSunday() {
        val result = getMonToFriTabIndexFrom(DateTimeConstants.SUNDAY)
        assertEquals(0, result)
    }

    @Test
    fun returnsFour_IfPutSaturday() {
        val result = getMonToFriTabIndexFrom(DateTimeConstants.SATURDAY)
        assertEquals(4, result)
    }

    @Test
    fun returnsZero_IfPutMonday() {
        val result = getMonToFriTabIndexFrom(DateTimeConstants.MONDAY)
        assertEquals(0, result)
    }

    @Test
    fun returnsFour_IfPutFriday() {
        val result = getMonToFriTabIndexFrom(DateTimeConstants.FRIDAY)
        assertEquals(4, result)
    }
}