package org.backend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "예상치 못한 서버 에러가 발생했습니다.";
    private static final String EXCEPTION_LOG_FORMAT = "[Exception Handler] {}";

    /**
     * 예측 가능한 예외를 처리한다.
     * @param e 커스텀 예외
     * @return 예외 내용
     */
    @ExceptionHandler(CustomException.class)
    public ProblemDetail handleCustomException(CustomException e) {
        log.warn(EXCEPTION_LOG_FORMAT, e.getMessage(), e);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                e.getErrorCode().getHttpStatus(), e.getErrorCode().getMessage());
        return new CustomProblemDetail(problemDetail, e.getErrorCode().name());
    }

    /**
     * 예측 불가능한 예외를 처리한다.
     * @param e 예외
     * @return 예외 내용
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleInternalServerException(Exception e) {
        log.error(EXCEPTION_LOG_FORMAT, e.getMessage(), e);

        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MESSAGE);
    }
}
