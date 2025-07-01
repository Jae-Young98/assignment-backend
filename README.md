# 과제 제출용 저장소
## Environment
JDK : 17  
Spring Boot : 3.3.13
cp-kafka : 8.0.x
> 과제2를 로컬에서 구동하기 위해선 카프카 컨테이너를 구동해야 합니다.
>```bash
>docker compose up -d
>```

## 과제1 - 반응형 API 서비스
### Preview
![step1](https://github.com/user-attachments/assets/c314f097-3322-4ce4-8da1-208d2de4f2cb)
[동영상](https://github.com/user-attachments/assets/ce9379bd-3e7d-4756-baf7-39dc073a2865)


### Requirements
- `WebFlux`를 활용하여 비동기 이벤트 스트림 기능을 구현한다.
- 한 번의 요청에 대해 `HelloWorld!` 문자열을 3번에 거쳐 각 3초, 2초, 1초 나눠서 응답한다.

### Sequence Diagram
![sse_flow](https://github.com/user-attachments/assets/50eae537-92f4-4ad4-abb8-14df7a8f12d3)

### Summary
WebFlux를 사용하여 구현하는건 처음이어서 기존에 사용하던 스프링 MVC와의 차이점을 알게 되었습니다.
1. 웹서버
기존 `org.springframework.boot:spring-boot-starter-web`와는 다르게 WebFlux를 사용하기 위해
`org.springframework.boot:spring-boot-starter-webflux` 의존성을 추가하였습니다.
스프링부트 실행 시 구동되는 서버는 기존 `Tomcat`에서 `Netty`로 변경됨을 알 수 있었습니다.
해당 과제는 각 3초, 2초, 1초의 딜레이가 있는데, 이 과정에서 Tomcat이라면 쓰레드를 점유하기 때문에
Netty보다 리소스 소모가 클 것이고 성능에도 유의미한 차이가 있을 것으로 판단 되었습니다.

2. 테스트코드
기존 스프링 MVC의 테스트코드 작성법으로는 WebFlux의 테스트를 진행할 수 없었습니다.
Servlet 기반인 `assertThat`등의 검증은 논블로킹 응답을 테스트하기 어렵고, 순차적 이벤트 발생과
딜레이를 검증하기 어려웠습니다. 또한 딜레이 테스트를 위해선 `Thread.sleep()` 등을 사용해야 했기에
효율적이지 못한 방법이었습니다.      
단순한 값 검증이 아닌 스트림의 순차적 이벤트 발생과 딜레이를 테스트하기 위해 WebFlux의 `StepVerifier`를 사용하였습니다.

## 과제2 - Kafka를 활용한 채팅 서버
### Preview
![ezgif-13d2132fea14fe](https://github.com/user-attachments/assets/71428387-c818-4677-9dec-20c91ab5f922)
[동영상](https://github.com/user-attachments/assets/86ba67f8-3146-474a-8746-b0b4e0dd6992)

### Requirements
- `Kafka`를 활용하여 실시간 이벤트 처리 기능을 구현한다.

### Sequence Diagram
![kafka-websocket](https://github.com/user-attachments/assets/719baa86-f989-4120-b222-2c9440459c29)

### Summary
Kafka를 이용해 실시간 채팅 서버를 구현하며 구조에 대해 한번 더 학습할 수 있었습니다.
1. KRaft
처음 구현할 때, Kafka와 Zookeeper 컨테이너를 이용하여 구현했습니다. 현재 과제 전형 환경에서는
성능, 관리 등을 고려해야할 정도는 아니지만, `KRaft`의 성능과 관리 포인트가 줄어든다는 장점을 보고 마이그레이션을 진행했습니다.
![0f8c7f4f103170fbeaa8de0e74d2457e2262150af100ef0ac9724715473bda04](https://github.com/user-attachments/assets/5366c503-44f3-4d37-8e16-fbbf823d9673)

2. 확장
현재는 메시지를 DB에 저장하는 기능은 없습니다. 하지만 Kafka의 특성을 고려하여 MSA로 확장한다면,
메시지 저장용 서비스 인스턴스를 구성하여 저장 혹은 배치로 구성할 수 있을거라 생각이 들었습니다.
또한 과제1에서 사용한 WebFlux와 Kafka를 같이 사용한다면 대규모 이벤트 처리와 실시간 응답에 있어서
유용하게 사용될 것으로 판단 되었습니다.
