# 한성대 공지

한성대 공지사항을 편하게 보기 위한 어플입니다. 키워드 알림, 즐겨찾기, 검색 기능을 가지고 있습니다.

[![](https://lh3.googleusercontent.com/cjsqrWQKJQp9RFO7-hJ9AfpKzbUb_Y84vXfjlP0iRHBvladwAfXih984olktDhPnFqyZ0nu9A5jvFwOEQPXzv7hr3ce3QVsLN8kQ2Ao=s0)](https://play.google.com/store/apps/details?id=com.foundy.hansungnotification)

# 스크린샷

|공지사항|즐겨찾기|
|------|---|
|![system structure](./images/screenshots/notice.jpg)|![system structure](./images/screenshots/favorite.jpg)|

|검색|알림 키워드|
|------|---|
|![system structure](./images/screenshots/search.jpg)|![system structure](./images/screenshots/keyword.jpg)|

# 시스템 구조도

![system structure](./images/system_structure.png)

# 아키텍쳐

![architecture](./images/architecture.png)

# 기술

- Clean architecture
- MVVM
- Hilt
- Paging
- Room
- Retrofit
- Flow
- Coroutine
- Firebase(messaging, realtime-database, auth)
- Espresso

# 키워드 알림

[위키 페이지](https://github.com/jja08111/HansungNotification/wiki/키워드-알림)에서 확인할 수 있습니다.


# 한성대 웹 사이트 리뉴얼시 대응 방법

- data 모듈 -> api 패키지 -> api 파일들의 URL을 수정한다.
- data 모듈 -> mapper 패키지 -> 스크래퍼를 수정한다.
- [HansungNotificationServer 레파지토리의 `scraper.py` 파일](https://github.com/jja08111/HansungNotificationServer/blob/main/src/scraper.py)을
  수정한다.

# Thanks to

이 프로젝트는 [아냥이](https://github.com/juhwankim-dev/pushNotificationApp) 구조를 참고하여 제작되었습니다.
[Juhwan Kim](https://github.com/juhwankim-dev)님께 감사드립니다.
