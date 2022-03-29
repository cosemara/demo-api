package com.project.sample.api.demo.exception;

public class CommonApiException  extends RuntimeException {

    private ExceptionCode code;
    private int httpStatus;
    private String message;

    public CommonApiException(ExceptionCode code) {
        this.code = code;
        this.httpStatus = code.getStatus();
        this.message = code.getMessage();
    }

    public CommonApiException(ExceptionCode code, int httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = code.getMessage();
    }

    public CommonApiException(ExceptionCode code, String errorMessage) {
        this.code = code;
        this.httpStatus = code.getStatus();
        this.message = errorMessage;
    }

    public CommonApiException(ExceptionCode code, int httpStatus, String errorMessage) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = errorMessage;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return code.getCode();
    }
}
