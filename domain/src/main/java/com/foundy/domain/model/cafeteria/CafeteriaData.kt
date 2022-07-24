package com.foundy.domain.model.cafeteria

data class CafeteriaData(
    /**
     * 월, 화, 수, 목, 금 순으로 메뉴가 있다.
     */
    val dailyMenus: List<DailyMenu>
)
