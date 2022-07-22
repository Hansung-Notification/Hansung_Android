package com.foundy.presentation

import com.foundy.domain.exception.AlreadyExistsException
import com.foundy.domain.exception.InvalidCharacterException
import com.foundy.domain.exception.MinLengthException
import com.foundy.domain.model.Keyword
import com.foundy.domain.usecase.keyword.ValidateKeywordUseCase
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateKeywordUseCaseTest {

    private val validateKeywordUseCase = ValidateKeywordUseCase()

    @Test
    fun `throws MinLengthException exception`() {
        assertThrows(MinLengthException::class.java) {
            validateKeywordUseCase("강", emptyList())
        }
    }

    @Test
    fun `throws AlreadyExistsException exception`() {
        assertThrows(AlreadyExistsException::class.java) {
            validateKeywordUseCase(
                "강하다",
                listOf(Keyword("최고야"), Keyword("강하다"))
            )
        }
    }

    @Test
    fun `throws InvalidCharacterException exception if keyword has blank`() {
        assertThrows(InvalidCharacterException::class.java) {
            validateKeywordUseCase(" 안녕", emptyList())
        }
    }

    @Test
    fun `throws InvalidCharacterException exception if keyword has English`() {
        assertThrows(InvalidCharacterException::class.java) {
            validateKeywordUseCase("wow", emptyList())
        }
    }

    @Test
    fun `throws InvalidCharacterException exception if keyword has not completed Korean`() {
        assertThrows(InvalidCharacterException::class.java) {
            validateKeywordUseCase("단어ㅋ", emptyList())
        }
    }

    @Test
    fun `returns true if characters of keyword is valid`() {
        assertTrue(validateKeywordUseCase("안1녕2", emptyList()))
    }
}