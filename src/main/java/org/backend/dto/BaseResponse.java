package org.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 공통 응답 포맷")
public record BaseResponse<T>(@Schema(description = "응답 데이터") T data) {
}
