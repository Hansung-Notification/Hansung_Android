package com.foundy.domain.usecase.keyword

import com.foundy.domain.exception.AlreadyExistsException
import com.foundy.domain.exception.InvalidCharacterException
import com.foundy.domain.exception.MinLengthException
import com.foundy.domain.model.Keyword
import java.util.regex.Pattern
import javax.inject.Inject

class ValidateKeywordUseCase @Inject constructor() {

    companion object {
        private const val MIN_LENGTH: Int = 2

        private val PATTERN: Pattern = Pattern.compile("^[0-9가-힣]+$")
    }

    operator fun invoke(keyword: String, registeredKeywordList: List<Keyword>): Boolean {
        if (keyword.length < MIN_LENGTH) throw MinLengthException()
        if (!PATTERN.matcher(keyword).matches()) throw InvalidCharacterException()
        if (registeredKeywordList.any { it.title == keyword }) throw AlreadyExistsException()
        return true
    }
}