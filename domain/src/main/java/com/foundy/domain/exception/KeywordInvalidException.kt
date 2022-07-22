package com.foundy.domain.exception

open class KeywordInvalidException(message: String) : Exception(message)

class MinLengthException : KeywordInvalidException("두 글자 이상 입력해주세요.")

class AlreadyExistsException : KeywordInvalidException("이미 존재하는 키워드입니다.")

class InvalidCharacterException : KeywordInvalidException("완성된 한글과 숫자만 입력할 수 있어요.")