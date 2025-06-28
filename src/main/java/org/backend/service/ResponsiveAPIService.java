package org.backend.service;

import java.time.Duration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ResponsiveAPIService {

    private static final String SUCCESS_CONNECT_MESSAGE = "Successfully Connected";

    /**
     * HelloWorld! 문자열을 딜레이와 함께 3번에 거쳐 응답한다.
     * @return HelloWorld!
     */
    public Flux<String> getHelloWorldMessage() {
        return Flux.concat(
                Flux.just(SUCCESS_CONNECT_MESSAGE),
                Mono.delay(Duration.ofSeconds(3)).thenMany(Mono.just("H").flux()),
                Mono.delay(Duration.ofSeconds(2)).thenMany(Mono.just("ello").flux()),
                Mono.delay(Duration.ofSeconds(1)).thenMany(Mono.just("World!").flux())
        );
    }
}
