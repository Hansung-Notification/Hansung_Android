package com.foundy.data.mapper

import com.foundy.data.di.NetworkModule
import com.foundy.domain.exception.ScrapingException
import com.foundy.domain.model.Notice
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.internal.StringUtil.isNumeric
import java.lang.Exception

sealed class NoticeMapper(private val isHeaderOnly: Boolean) {

    /**
     * 헤더 공지사항은 제외한 공지만 뽑는 오브젝트이다.
     */
    object NonHeader : NoticeMapper(false)

    /**
     * 헤더 공지사항만 뽑아오는 오브젝트이다.
     */
    object Header : NoticeMapper(true)

    operator fun invoke(responseBody: ResponseBody): List<Notice> {
        try {
            val doc = Jsoup.parse(responseBody.string())
            val tbody = doc.getElementsByTag("tbody").first()!!
            val noticeList = tbody.getElementsByTag("tr")
            val result = ArrayList<Notice>(noticeList.size)

            noticeList.forEach {
                val subject = it.select(".td-subject")
                val href = it.select("a[href]").attr("href")
                val number = it.select(".td-num").text()
                val isHeader = !isNumeric(number)
                val isNew = subject.select(".new").isNotEmpty()
                val title = subject.text()
                val writer = it.select(".td-write").text()
                val date = it.select(".td-date").text()

                if ((isHeader && isHeaderOnly) || (!isHeader && !isHeaderOnly)) {
                    result.add(
                        Notice(
                            isHeader = isHeader,
                            isNew = isNew,
                            title = title,
                            date = date,
                            writer = writer,
                            url = NetworkModule.BASE_URL + href
                        )
                    )
                }
            }
            return result
        } catch (e: Exception) {
            throw ScrapingException()
        }
    }
}