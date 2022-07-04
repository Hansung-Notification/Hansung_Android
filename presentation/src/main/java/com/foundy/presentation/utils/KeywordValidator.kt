package com.foundy.presentation.utils

import com.foundy.domain.model.Keyword
import java.util.regex.Pattern

object KeywordValidator {

    private const val MIN_LENGTH: Int = 2

    private val PATTERN: Pattern = Pattern.compile("^[0-9가-힣]+$")

    open class KeywordInvalidException(message: String) : Exception(message)
    class MinLengthException : KeywordInvalidException("두 글자 이상 입력해주세요.")
    class AlreadyExistsException : KeywordInvalidException("이미 존재하는 키워드입니다.")
    class InvalidCharacterException : KeywordInvalidException("완성된 한글과 숫자만 입력할 수 있어요.")

    operator fun invoke(keyword: String, registeredKeywordList: List<Keyword>): Boolean {
        if (!PATTERN.matcher(keyword).matches()) throw InvalidCharacterException()
        if (registeredKeywordList.any { it.title == keyword }) throw AlreadyExistsException()
        if (keyword.length < MIN_LENGTH) throw MinLengthException()
        return true
    }
}