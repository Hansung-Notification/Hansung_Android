package com.foundy.domain.model.cafeteria

import org.joda.time.DateTimeConstants
import java.lang.IllegalArgumentException

data class CafeteriaData(
    /**
     * 월, 화, 수, 목, 금 순으로 메뉴가 있어야 한다.
     */
    private val dailyMenus: List<DailyMenu>
) {
    fun dailyMenuAt(weekday: Int) {
        when (weekday) {
            DateTimeConstants.MONDAY -> dailyMenus[0]
            DateTimeConstants.TUESDAY -> dailyMenus[1]
            DateTimeConstants.WEDNESDAY -> dailyMenus[2]
            DateTimeConstants.THURSDAY -> dailyMenus[3]
            DateTimeConstants.FRIDAY -> dailyMenus[4]
            else -> throw IllegalArgumentException()
        }
    }
}
