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
            KeywordValidator.check("강", emptyList())
        }
    }

    @Test
    fun `throws AlreadyExistsException exception`() {
        assertThrows(KeywordValidator.AlreadyExistsException::class.java) {
            KeywordValidator.check(
                "강하다",
                listOf(Keyword("최고야"), Keyword("강하다"))
            )
        }
    }

    @Test
    fun `throws InvalidCharacterException exception if keyword has blank`() {
        assertThrows(KeywordValidator.InvalidCharacterException::class.java) {
            KeywordValidator.check(" 안녕", emptyList())
        }
    }

    @Test
    fun `throws InvalidCharacterException exception if keyword has English`() {
        assertThrows(KeywordValidator.InvalidCharacterException::class.java) {
            KeywordValidator.check("wow", emptyList())
        }
    }

    @Test
    fun `throws InvalidCharacterException exception if keyword has not completed Korean`() {
        assertThrows(KeywordValidator.InvalidCharacterException::class.java) {
            KeywordValidator.check("단어ㅋ", emptyList())
        }
    }

    @Test
    fun `returns true if characters of keyword is valid`() {
        assertTrue(KeywordValidator.check("안1녕2", emptyList()))
    }
}