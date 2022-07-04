package com.foundy.presentation

import com.foundy.domain.model.Keyword
import com.foundy.presentation.utils.KeywordValidator
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class KeywordValidatorTest {

    @Test
    fun `throws MinLengthException exception`() {
        assertThrows(KeywordValidator.MinLengthException::class.java) {
            KeywordValidator(
                "강",
                emptyList()
            )
        }
    }

    @Test
    fun `throws AlreadyExistsException exception`() {
        assertThrows(KeywordValidator.AlreadyExistsException::class.java) {
            KeywordValidator(
                "강하다",
                listOf(Keyword("최고야"), Keyword("강하다"))
            )
        }
    }

    @Test
    fun `throws InvalidCharacterException exception`() {
        assertThrows(KeywordValidator.InvalidCharacterException::class.java) {
            KeywordValidator(
                " f",
                emptyList()
            )
        }
    }

    @Test
    fun `returns true if characters of keyword is valid`() {
        assertTrue(KeywordValidator("안녕12ㅋ", emptyList()))
    }
}