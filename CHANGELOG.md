# [v1.1.3](https://github.com/jja08111/HansungNotification/releases/tag/v1.1.3)

- 학식 정보 누락되지 않도록 cafeteria 모델 단순화

# [v1.1.2](https://github.com/jja08111/HansungNotification/releases/tag/v1.1.2)

- 학식 정보 일부 누락되던 문제 수정 ([#44](https://github.com/jja08111/HansungNotification/pull/44))
- 당겨서 새로고침 기능 추가 ([#45](https://github.com/jja08111/HansungNotification/pull/45))

# [v1.1.1](https://github.com/jja08111/HansungNotification/releases/tag/v1.1.1)
- 웹뷰에서 파일 다운로드 시 파일 이름 끝에 세미 콜론 붙던 문제 수정 ([#41](https://github.com/jja08111/HansungNotification/pull/41))

# [v1.1.0](https://github.com/jja08111/HansungNotification/releases/tag/v1.1.0)
- 학식 기능 추가 ([#38](https://github.com/jja08111/HansungNotification/pull/38))
- build.gradle 리팩토링 ([#37](https://github.com/jja08111/HansungNotification/pull/37))
- KeywordValidator를 ValidateKeywordUseCase로 리팩토링 ([#36](https://github.com/jja08111/HansungNotification/pull/36))

# [v1.0.0](https://github.com/jja08111/HansungNotification/releases/tag/v1.0.0)
- **앱 출시** 
- POST 요청하는 API 코드 단순화
- `UiState` 패턴 적극적으로 이용하도록 리팩토링
- 잘못된 `callbackFlow`로 인한 앱 충돌 수정 ([#34](https://github.com/jja08111/HansungNotification/pull/34))
- Database reference를 의존성 주입을 통해 이용하도록 수정

# [v1.0.0-BETA3](https://github.com/jja08111/HansungNotification/releases/tag/v1.0.0-BETA3)
- 키워드 생성, 삭제할 때 스낵바를 보이도록 수정
- Favorite StateFlow를 기존 ViewModel에서 저장했으나 Repository로 이동하여 하나로 관리하도록 수정
- UI쪽에서 lifecycle관련하여 StateFlow를 잘못 사용하던 코드 수정
- 키워드 알림 수신시 DB에 해당 키워드가 있는 지 확인하도록 수정([#29](https://github.com/jja08111/HansungNotification/pull/29))

# [v1.0.0-BETA2](https://github.com/jja08111/HansungNotification/releases/tag/v1.0.0-BETA2)

- 코드 리팩토링
- 기기가 offline인 상태에서도 Firebase realtime-database가 동작하도록 persistence를 활성화 함
- 앱을 실행할 때마다 Firebase DB의 키워드를 다시 구독하도록 함 ([#24](https://github.com/jja08111/HansungNotification/issues/24))

# [v1.0.0-BETA1](https://github.com/jja08111/HansungNotification/releases/tag/v1.0.0-BETA1)

- 첫 베타 버전 출시