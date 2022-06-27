package com.foundy.presentation.factory

import com.foundy.domain.model.Notice

enum class NoticeType {
    HEADER,
    NEW,
    NORMAL
}

object NoticeFactory {

    fun create(noticeType: NoticeType): Notice {
        return when (noticeType) {
            NoticeType.HEADER -> Notice(
                isHeader = true,
                isNew = false,
                title = "2023학년도 학석사연계과정 합격자 발표",
                date = "2022.06.24",
                writer = "대학원 교학팀",
                url = "https://www.hansung.ac.kr//bbs/hansung/143/247276/artclView.do"
            )
            NoticeType.NEW -> Notice(
                isHeader = false,
                isNew = true,
                title = "2022 전력거래소 창업 해커톤 경진대회",
                date = "2022.06.27",
                writer = "학생장학팀",
                url = "https://www.hansung.ac.kr//bbs/hansung/143/247301/artclView.do"
            )
            NoticeType.NORMAL -> Notice(
                isHeader = false,
                isNew = false,
                title = "[채용] 역사문화학부 조교 채용 공고",
                date = "2022.06.27",
                writer = "김미영",
                url = "https://www.hansung.ac.kr//bbs/hansung/143/247300/artclView.do"
            )
        }
    }
}