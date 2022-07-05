package com.foundy.domain.model

import java.util.*

data class Query(
    val content: String,
    val date: String = Date().toString()
)