package org.backend.service;

import java.time.Duration;
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
                .expectNext("Successfully Connected")
                .thenAwait(Duration.ofSeconds(3))
                .expectNext("H")
                .thenAwait(Duration.ofSeconds(2))
                .expectNext("ello")
                .thenAwait(Duration.ofSeconds(1))
                .expectNext("World!")
                .verifyComplete();
    }
}