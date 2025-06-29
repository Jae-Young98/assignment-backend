package org.backend.service;

import java.time.Duration;
import org.backend.dto.response.SseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class ResponsiveAPIServiceTest {

    @Autowired
    private ResponsiveAPIService responsiveAPIService;

    @Test
    void 문자열을_딜레이와_함께_3번에_거쳐_응답한다() {
        StepVerifier.withVirtualTime(() -> responsiveAPIService.getHelloWorldMessage())
                .expectSubscription()
                .expectNext(new SseResponse("Successfully Connected", false))
                .thenAwait(Duration.ofSeconds(3))
                .expectNext(new SseResponse("H", false))
                .thenAwait(Duration.ofSeconds(2))
                .expectNext(new SseResponse("ello", false))
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(new SseResponse("World!", true))
                .verifyComplete();
    }
}