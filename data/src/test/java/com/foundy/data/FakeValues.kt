package com.foundy.data

const val fakeNoticeResponseContent = """
<html>
  <head> </head>
  <body>
    <table class="board-table horizon1">
      <caption>
        공지사항
      </caption>
      <colgroup>
        <col class="col-num" />
        <col class="col-subject" />
        <col class="col-write" />
        <col class="col-date" />

        <col class="col-acess" />

        <col class="col-file" />
      </colgroup>
      <thead>
        <tr>
          <th class="th-num">번호</th>
          <th class="th-subject">제목</th>
          <th class="th-write">작성자</th>
          <th class="th-date">작성일</th>

          <th class="th-acess">조회수</th>

          <th class="th-file">첨부파일</th>
        </tr>
      </thead>
      <tbody>
        <tr class="notice">
          <td class="td-num">
            <span>일반공지</span>
          </td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247276/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247276')"
            >
              <strong>
                <span> </span>
                2023학년도 학석사연계과정 합격자 발표
              </strong>
            </a>
          </td>
          <td class="td-write">대학원 교학팀</td>
          <td class="td-date">2022.06.24</td>

          <td class="td-access">249</td>

          <td class="td-file"></td>
        </tr>

        <tr class="notice">
          <td class="td-num">
            <span>일반공지</span>
          </td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/240424/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '240424')"
            >
              <strong>
                <span> </span>
                [공지] 비교과교육과정 및 비교과포인트 제도 안내
              </strong>
            </a>
          </td>
          <td class="td-write">학생성공센터</td>
          <td class="td-date">2021.10.21</td>

          <td class="td-access">5629</td>

          <td class="td-file">3</td>
        </tr>

        <tr class="">
          <td class="td-num">8510</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247284/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247284')"
            >
              <strong> [연장][채용]산학협력단 학술연구원 모집 공고 </strong>
            </a>
          </td>
          <td class="td-write">과제지원팀</td>
          <td class="td-date">2022.06.24</td>

          <td class="td-access">81</td>

          <td class="td-file">1</td>
        </tr>

        <tr class="">
          <td class="td-num">8509</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247279/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247279')"
            >
              <strong>
                [재공지]2022-1학기 학기중 창업자 비교과포인트 신청 안내
              </strong>
            </a>
          </td>
          <td class="td-write">창업지원센터</td>
          <td class="td-date">2022.06.24</td>

          <td class="td-access">98</td>

          <td class="td-file">1</td>
        </tr>

        <tr class="">
          <td class="td-num">8508</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247274/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247274')"
            >
              <strong> [채용] IT융합공학부 실습조교 채용공고 </strong>
            </a>
          </td>
          <td class="td-write">IT융합공학부</td>
          <td class="td-date">2022.06.24</td>

          <td class="td-access">308</td>

          <td class="td-file">1</td>
        </tr>

        <tr class="">
          <td class="td-num">8507</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247276/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247276')"
            >
              <strong> 2023학년도 학석사연계과정 합격자 발표 </strong>
            </a>
          </td>
          <td class="td-write">대학원 교학팀</td>
          <td class="td-date">2022.06.24</td>

          <td class="td-access">249</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8506</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247264/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247264')"
            >
              <strong>
                [한국교육개발원] 2022 대학의 교수·학습 질 제고 전략 탐색 연구
                ...
              </strong>
            </a>
          </td>
          <td class="td-write">교수학습센터</td>
          <td class="td-date">2022.06.23</td>

          <td class="td-access">140</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8505</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247263/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247263')"
            >
              <strong> 2022-1학기 상시진로지도시스템 종료 안내 </strong>
            </a>
          </td>
          <td class="td-write">진로취업지원팀</td>
          <td class="td-date">2022.06.23</td>

          <td class="td-access">239</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8504</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247262/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247262')"
            >
              <strong> 위메이저 (전공강연단체) 여름기수 봉사자 모집 </strong>
            </a>
          </td>
          <td class="td-write">학생장학팀</td>
          <td class="td-date">2022.06.23</td>

          <td class="td-access">163</td>

          <td class="td-file">2</td>
        </tr>

        <tr class="">
          <td class="td-num">8503</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247261/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247261')"
            >
              <strong>
                [비교과 50pt] 한성대학교 대학혁신지원사업 홍보단 모집
              </strong>
            </a>
          </td>
          <td class="td-write">혁신지원사업센터</td>
          <td class="td-date">2022.06.23</td>

          <td class="td-access">190</td>

          <td class="td-file">2</td>
        </tr>

        <tr class="">
          <td class="td-num">8502</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247258/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247258')"
            >
              <strong> 2022학년도 2학기 창업대체학점 신청자 모집 </strong>
            </a>
          </td>
          <td class="td-write">창업지원센터</td>
          <td class="td-date">2022.06.23</td>

          <td class="td-access">47</td>

          <td class="td-file">2</td>
        </tr>

        <tr class="">
          <td class="td-num">8501</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247256/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247256')"
            >
              <strong> 2022학년도 2학기 창업휴학 신청 안내 </strong>
            </a>
          </td>
          <td class="td-write">창업지원센터</td>
          <td class="td-date">2022.06.23</td>

          <td class="td-access">61</td>

          <td class="td-file">1</td>
        </tr>

        <tr class="">
          <td class="td-num">8500</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247252/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247252')"
            >
              <strong> 2022년 인문대학생 아카데미 모집 홍보 </strong>
            </a>
          </td>
          <td class="td-write">인문예술대학 교학팀</td>
          <td class="td-date">2022.06.23</td>

          <td class="td-access">164</td>

          <td class="td-file">3</td>
        </tr>

        <tr class="">
          <td class="td-num">8499</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247248/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247248')"
            >
              <strong> 진로취업지원팀 행정조교 채용 </strong>
            </a>
          </td>
          <td class="td-write">취업지원팀</td>
          <td class="td-date">2022.06.22</td>

          <td class="td-access">812</td>

          <td class="td-file">1</td>
        </tr>

        <tr class="">
          <td class="td-num">8498</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247245/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247245')"
            >
              <strong>
                [공고] 2022년 한성대학교 캠퍼스타운 「한성 ACT 스타트업 공모 ...
              </strong>
            </a>
          </td>
          <td class="td-write">캠퍼스타운사업단</td>
          <td class="td-date">2022.06.22</td>

          <td class="td-access">112</td>

          <td class="td-file">3</td>
        </tr>

        <tr class="">
          <td class="td-num">8497</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247244/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247244')"
            >
              <strong>
                한성대학교 컴퓨터공학부 실습조교(주간) 모집 안내
              </strong>
            </a>
          </td>
          <td class="td-write">컴퓨터공학부</td>
          <td class="td-date">2022.06.22</td>

          <td class="td-access">198</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8496</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247227/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247227')"
            >
              <strong>
                [비교과 50pt] 2022-1학기 HS-TED 학생강사 모집안내
              </strong>
            </a>
          </td>
          <td class="td-write">교수학습센터</td>
          <td class="td-date">2022.06.21</td>

          <td class="td-access">355</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8495</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247223/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247223')"
            >
              <strong>
                [재공고] 교육혁신원 스마트원격교육센터 계약직 직원 채용공고
              </strong>
            </a>
          </td>
          <td class="td-write">스마트원격교육센터</td>
          <td class="td-date">2022.06.21</td>

          <td class="td-access">122</td>

          <td class="td-file">3</td>
        </tr>

        <tr class="">
          <td class="td-num">8494</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247179/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247179')"
            >
              <strong>
                선택 트랙 간 상호(제1트랙⇄제2트랙) 전공과목 변경신청 추가시행 안
                ...
              </strong>
            </a>
          </td>
          <td class="td-write">학사지원팀</td>
          <td class="td-date">2022.06.21</td>

          <td class="td-access">431</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8493</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247207/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247207')"
            >
              <strong> [비교과 10pt]2022-1학기 취업전략 특강 신청안내 </strong>
            </a>
          </td>
          <td class="td-write">진로취업지원팀</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">485</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8492</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247206/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247206')"
            >
              <strong> 2022-하계방중 상상파크 운영 시간 변경 안내 </strong>
            </a>
          </td>
          <td class="td-write">창의융합교육지원센터</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">281</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8491</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247200/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247200')"
            >
              <strong> 한성대학교 학교기업 레스토랑 상호명 공모전 안내 </strong>
            </a>
          </td>
          <td class="td-write">과제지원팀</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">899</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8490</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247196/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247196')"
            >
              <strong>
                [채용/대학혁신지원사업] 학생성공센터 학술연구원 채용공고
              </strong>
            </a>
          </td>
          <td class="td-write">학생성공센터</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">132</td>

          <td class="td-file">3</td>
        </tr>

        <tr class="">
          <td class="td-num">8489</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247188/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247188')"
            >
              <strong> [채용] 시각·영상디자인전공 행정조교 채용공고 </strong>
            </a>
          </td>
          <td class="td-write">ICT디자인학부</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">105</td>

          <td class="td-file">1</td>
        </tr>

        <tr class="">
          <td class="td-num">8488</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247181/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247181')"
            >
              <strong> 한성대학교 대학혁신지원사업 홍보단 모집 </strong>
            </a>
          </td>
          <td class="td-write">혁신지원사업센터</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">177</td>

          <td class="td-file">2</td>
        </tr>

        <tr class="">
          <td class="td-num">8487</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247180/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247180')"
            >
              <strong> [추천채용] 대림비앤코주식회사 </strong>
            </a>
          </td>
          <td class="td-write">학생진로상담센터</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">176</td>

          <td class="td-file">2</td>
        </tr>

        <tr class="">
          <td class="td-num">8486</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247177/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247177')"
            >
              <strong>
                [기간 연장 - 비교과 최대 60P] 2022학년도 여름방학 집단상담 ...
              </strong>
            </a>
          </td>
          <td class="td-write">학생생활상담센터</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">515</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8485</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247176/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247176')"
            >
              <strong> [비교과10pt] 개교 50주년 기념 입체조형물 공모전 </strong>
            </a>
          </td>
          <td class="td-write">창의융합교육지원센터</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">399</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8484</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247175/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247175')"
            >
              <strong> [교보문고] 창작의 날씨, 창작 원정대 모집 홍보 </strong>
            </a>
          </td>
          <td class="td-write">인문예술대학 교학팀</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">211</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8483</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247173/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247173')"
            >
              <strong>
                2022년 상반기 공공기관 혁신 아이디어 공모전 (~7/8)
              </strong>
            </a>
          </td>
          <td class="td-write">지식재산교육센터</td>
          <td class="td-date">2022.06.20</td>

          <td class="td-access">124</td>

          <td class="td-file"></td>
        </tr>

        <tr class="">
          <td class="td-num">8482</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247168/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247168')"
            >
              <strong> 22년 7월 각 군 현역병 모집일정 </strong>
            </a>
          </td>
          <td class="td-write">학생장학팀</td>
          <td class="td-date">2022.06.17</td>

          <td class="td-access">284</td>

          <td class="td-file">1</td>
        </tr>

        <tr class="">
          <td class="td-num">8481</td>
          <td class="td-subject">
            <a
              href="/bbs/hansung/143/247167/artclView.do"
              onclick="jf_viewArtcl('hansung', '143', '247167')"
            >
              <strong>
                [비교과 10포인트] 학생생활상담센터 6월「온라인 힐링데이
                프로그램」 ...
              </strong>
            </a>
          </td>
          <td class="td-write">학생생활상담센터</td>
          <td class="td-date">2022.06.17</td>

          <td class="td-access">854</td>

          <td class="td-file"></td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
"""