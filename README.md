# 과제 제출용 저장소
## Environment
JDK : 17  
Spring Boot : 3.3.13
## 과제1 - 반응형 API 서비스
### Preview
![step1](https://github.com/user-attachments/assets/c314f097-3322-4ce4-8da1-208d2de4f2cb)

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
