package org.backend.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    INTERNAL_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 예외가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
