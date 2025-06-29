package org.backend.controller;

import lombok.RequiredArgsConstructor;
import org.backend.dto.BaseResponse;
import org.backend.dto.response.SseResponse;
import org.backend.service.ResponsiveAPIService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class ResponsiveAPIController {

    private final ResponsiveAPIService responsiveAPIService;

    @GetMapping(value = "/messages/greeting", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<BaseResponse<SseResponse>> getHelloWorldMessage() {
        return responsiveAPIService.getHelloWorldMessage()
                .map(BaseResponse::new);
    }
}
