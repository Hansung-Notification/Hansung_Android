package com.foundy.data.mapper

import com.foundy.domain.model.cafeteria.CafeteriaData
import com.foundy.domain.model.cafeteria.DailyMenu
import com.foundy.domain.model.cafeteria.Menu
import com.foundy.domain.model.cafeteria.MenuGroup
import okhttp3.ResponseBody
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.jsoup.Jsoup

object CafeteriaMapper {

    operator fun invoke(responseBody: ResponseBody): CafeteriaData {
        val doc = Jsoup.parse(responseBody.string())
        val tableBody = doc.getElementsByTag("tbody").first()
        val tableRows = tableBody?.children()
        val dailyMenus = mutableListOf<DailyMenu>()

        if (tableRows == null) throw Exception()

        for (tableRow in tableRows) {
            val dateTimeString = tableRow
                .getElementsByTag("th")
                .first()
                ?.text()
                ?.split(" ")
                ?.first()
            val formatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyy.MM.dd")
            val dateTime =
                if (dateTimeString != null) formatter.parseDateTime(dateTimeString) else null
            val tableDataList = tableRow.getElementsByTag("td")

            // 식단 구분, 식단 이 두 개가 없다면 건너 뛴다.
            if (tableDataList.size < 2) continue

            val groupName = tableDataList[0].text()
            val menusAndPrices = tableDataList[1].text().split(" ")
            val menus = mutableListOf<Menu>()

            for (index: Int in (0..menusAndPrices.size - 2) step 2) {
                try {
                    val name = menusAndPrices[index]
                    val price = menusAndPrices[index + 1].replace(",", "").toInt()
                    val menu = Menu(name, price)
                    menus.add(menu)
                } catch (e: NumberFormatException) { // 식단이 없는 경우 변환에 실패한다.
                    break
                }
            }

            val menuGroups = listOf(MenuGroup(groupName, menus))

            if (dateTime != null) { // 해당 날짜의 첫 번째 식단인 경우 새로 넣는다.
                dailyMenus.add(DailyMenu(dateTime, menuGroups))
            } else { // 날짜가 바뀌지 않은 경우 메뉴 그룹을 마지막 식단에 추가한다.
                val lastDailyMenus = dailyMenus.last()
                val newDailyMenu = lastDailyMenus.copy(
                    menuGroups = lastDailyMenus.menuGroups + menuGroups
                )
                dailyMenus[dailyMenus.lastIndex] = newDailyMenu
            }
        }

        return CafeteriaData(dailyMenus)
    }
}