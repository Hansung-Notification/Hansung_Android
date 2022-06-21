package com.foundy.data.mapper

import com.foundy.domain.model.Notice
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.internal.StringUtil.isNumeric

object NoticeMapper {
    operator fun invoke(responseBody: ResponseBody): List<Notice> {
        val doc = Jsoup.parse(responseBody.string())
        val tbody = doc.getElementsByTag("tbody").first()!!
        val noticeList = tbody.getElementsByTag("tr")
        val result = ArrayList<Notice>(noticeList.size)

        noticeList.forEach {
            val subject = it.select(".td-subject")
            val href = subject.attr("href")
            val number = it.select(".td-num").text()
            val isHeader = !isNumeric(number)
            val isNew = subject.select(".new").isNotEmpty()
            val title = subject.text()
            val writer = it.select(".td-write").text()
            val date = it.select(".td-date").text()

            result.add(
                Notice(
                    isHeader = isHeader,
                    isNew = isNew,
                    title = title,
                    date = date,
                    writer = writer,
                    href = href
                )
            )
        }

        return result
    }
}