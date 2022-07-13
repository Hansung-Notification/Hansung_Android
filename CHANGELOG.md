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