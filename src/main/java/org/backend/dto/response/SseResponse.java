package org.backend.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "SSE 응답 포맷")
public record SseResponse(@Schema(description = "SSE 메시지") String message,
                          @Schema(description = "완료 여부") boolean complete) {

}
