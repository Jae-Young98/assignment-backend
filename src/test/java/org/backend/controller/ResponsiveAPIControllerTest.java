package org.backend.controller;

import org.backend.dto.BaseResponse;
import org.backend.dto.response.SseResponse;
import org.backend.service.ResponsiveAPIService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@WebFluxTest(controllers = ResponsiveAPIController.class)
class ResponsiveAPIControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ResponsiveAPIService responsiveAPIService;

    @Test
    void 반응형_API_조회를_요청한다() {
        Flux<SseResponse> expected = Flux.just(
                new SseResponse("Successfully Connected", false),
                new SseResponse("H", false),
                new SseResponse("ello", false),
                new SseResponse("World!", true));

        Mockito.when(responsiveAPIService.getHelloWorldMessage())
                .thenReturn(expected);

        webTestClient.get()
                .uri("/messages/greeting")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(new ParameterizedTypeReference<BaseResponse<SseResponse>>() {})
                .getResponseBody()
                .collectList()
                .as(StepVerifier::create)
                .expectNextMatches(list -> list.size() == 4 &&
                        list.get(0).data().equals(new SseResponse("Successfully Connected", false)) &&
                        list.get(1).data().equals(new SseResponse("H", false)) &&
                        list.get(2).data().equals(new SseResponse("ello", false)) &&
                        list.get(3).data().equals(new SseResponse("World!", true)))
                .verifyComplete();
    }
}