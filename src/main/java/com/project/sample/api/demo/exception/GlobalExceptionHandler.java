package com.project.sample.api.demo.exception;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = CommonApiException.class)
    public ResponseEntity<ErrorResult> error(CommonApiException exception) {
        return new ResponseEntity<>(ErrorResult.of(exception), HttpStatus.valueOf(exception.getHttpStatus()));
    }

    @Value
    @Builder
    static class ErrorResult{
        private String code;
        private String message;

        static ErrorResult of(CommonApiException commonApiException){
            return ErrorResult
                    .builder()
                    .code(commonApiException.getErrorCode())
                    .message(commonApiException.getMessage())
                    .build();
        }
    }
}
