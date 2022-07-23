package com.foundy.domain.model.cafeteria

import org.joda.time.DateTime

data class DailyMenu(
    val date: DateTime,
    val menuGroups: List<MenuGroup>
)
