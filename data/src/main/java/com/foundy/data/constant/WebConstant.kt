package com.foundy.data.constant


object WebConstant {

    const val START_PAGE = 1

    /**
     * Post 요청에 쓰일 파라미터를 생성한다.
     *
     * 만약 추후에 한성대 사이트가 리뉴얼 되는 경우 크롬 검사에 들어가 네트워크 탭에서 해당 파일을 고른 후 페이로드 항목을
     * 확인하면 된다.
     */
    fun createSearchPostParameter(query: String): HashMap<String, Any> {
        return hashMapOf(
            "srchWrd" to query,
            "srchColumn" to "sj"
        )
    }
}