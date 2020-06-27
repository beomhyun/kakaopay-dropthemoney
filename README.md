# 핵심 문제해결 전략

## 개발 환경
- Java
- Gradle
- Spring boot
- JPA
- H2 (In memory DB)
- LOMBOK

## DB
- USER
  - ID , BALANCE(잔액), NAME, CREATE_AT, UPDATE_AT
- ROOM
  - ID, CREATE_AT, UPDATE_AT
- ROOM_MEMBER
  - ID, ROOM_ID, USER_ID, CREATE_AT, UPDATE_AT
- DROP_MONEY
  - ID, TOKEN, TOTAL_MONEY, DROP_USER_ID(FK), ROOM_ID(FK), CREATE_AT, UPDATE_AT
- RECEIVE_INFO
  - ID, MONEY, DROP_MONEY_ID(FK), RECEIVE_USER_ID(FK), CREATE_AT, UPDATE_AT
  
## 1. 뿌리기 API
- token
  - Java.util 의 Random 클래스를 사용하여 아스키 코드의 33 ~ 126의 문자열 중 3개의 문자로 3자리 문자열 생성
  - JPA의 findByToken 메서드로 이미 사용중인 token 확인 후 재생성
- 분배
  - 인원수당 1원씩 미리 분배 후 남은 금액이 없을 때 까지 1~남은금액 중의 임의의 금액을 분배

## 2. 받기 API
- 제한 사항
  - 뿌리기 당 한 사용자는 한번만 받을 수 있습니다.
  - 자신이 뿌리기 한 건은 자신이 받을 수 없습니다.
  - 뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.
  - 뿌린 건은 10분간만 유효합니다.
  - 각 제한 사항에 대한 실패 응답
- 할당
  - token에 해당하는 뿌리기 건 중 아직 할당되지 않은 금액 랜덤하게 할당 후 해당 데이터에 대한 받은 사용자 업데이트

## 3. 조회 API
- 제한 사항
  - 뿌린 사람만 조회를 할 수 있습니다.
  - 유효하지 않은 token
  - 뿌린 건에 대한 조회는 7일 동안 할 수 있습니다.
  - 각 제한 사항에 대한 실패 응답
- token에 해당하는 뿌리기 건의 현재 상태
  - 뿌린 시각, 뿌린 금액, 받기 완료된 금액, 받기 완료된 정보([받은 금액, 받은 사용자 아이디] 리스트)
  - DTO 생성후 응답 데이터로 사용

## TO DO
- test code
  - Junit, Mockito 버전 및 의존성 관련 이슈 발생으로 해결 하던 중 과제 시간안에 완료하지 못하였습니다.
  
