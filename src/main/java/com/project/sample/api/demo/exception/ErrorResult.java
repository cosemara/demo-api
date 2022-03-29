package com.project.sample.api.demo.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResult {
    private String code;
    private String message;

    static GlobalExceptionHandler.ErrorResult of(CommonApiException bookStoreException){
        return GlobalExceptionHandler.ErrorResult
                .builder()
                .code(bookStoreException.getErrorCode())
                .message(bookStoreException.getMessage())
                .build();
    }
}
