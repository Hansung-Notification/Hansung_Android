package com.foundy.presentation.utils

import androidx.annotation.VisibleForTesting
import com.foundy.domain.model.Keyword
import java.util.regex.Pattern

object KeywordValidator {

    @VisibleForTesting
    const val MIN_LENGTH: Int = 2

    @VisibleForTesting
    val PATTERN: Pattern = Pattern.compile("^[0-9ㄱ-ㅎ가-힣]+$")

    class MinLengthException : Exception("두 글자 이상 입력해주세요.")
    class AlreadyExistsException : Exception("이미 존재하는 키워드입니다.")
    class InvalidCharacterException : Exception("한글과 숫자만 입력할 수 있어요.")

    operator fun invoke(keyword: String, registeredKeywordList: List<Keyword>): Boolean {
        if (keyword.length < MIN_LENGTH) throw MinLengthException()
        if (registeredKeywordList.any { it.title == keyword }) throw AlreadyExistsException()
        if (!PATTERN.matcher(keyword).matches()) throw InvalidCharacterException()
        return true
    }
}