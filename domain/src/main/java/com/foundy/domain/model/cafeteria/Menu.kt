package com.foundy.domain.model.cafeteria

import java.text.NumberFormat
import java.util.*

data class Menu(
    val name: String,
    val price: Int
) {
    val priceWithComma: String
        get() = NumberFormat.getNumberInstance(Locale.US).format(price)
}
