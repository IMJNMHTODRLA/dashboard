# Dashboard

### Minecraft 서버 상태를 웹으로 확인하고, 콘솔에서 TPS, CPU, 메모리 사용량 등을 확인할 수 있는 플러그인입니다.

## 설치
1. `dashboard-1.0.0.jar`를 서버의 `plugins` 폴더에 넣습니다.
2. 서버를 재시작합니다.
3. `/dashboard` 명령어로 사용 가능합니다.

## 사용 방법
- `/dashboard reload` : config.yml을 다시 불러옵니다.
- `/dashboard tps` : 1분전 서버 TPS을 확인합니다.
- `/dashboard cpu` : CPU 사용량을 확인합니다.
- `/dashboard memory` : 메모리 사용량을 확인합니다.
- `/dashboard player` : 현재 서버에 접속 중인 플레이어 수를 출력합니다.
- `/dashboard web` : 웹 대시보드를 엽니다. (콘솔 전용)

## 개발 환경
- Kotlin
- Bukkit / Paper API
- Java 8

## 참고
- 웹 대시보드는 `localhost:9999`에서 접근이 가능합니다.(config.yml에서 포트 변경 가능합니다.)
- Paper API를 사용하였습니다.
- 버전은 1.13이상입니다.