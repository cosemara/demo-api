package com.project.sample.api.demo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    SUCCESS(200, "0000", "성공"),

    // header
    INVALID_HEADER(400, "1000", "유효하지 않은 헤더 입니다"),

    // param
    INVALID_PARAM(400, "1001", "유효하지 않은 파라미터 입니다"),

    USER_NOT_FOUND(400, "2002", "유저 정보를 찾을 수 없습니다"),
    NOT_FOUND_ASSETS(500, "2003", "등록되지 않은 자산입니다."),
    FAIL_ASSETS_ADD(500, "2004", "자산등록에 실패하였습니다"),
    FAIL_ASSETS_UPD(500, "2005", "자산수정에 실패하였습니다"),
    FAIL_ASSETS_DEL(500, "2006", "자산삭제에 실패하였습니다"),

    ALREADY_EXIST_EMAIL(500, "2007","이미 존재하는 이메일주소입니다."),
    ALREADY_EXIST_ASSETS(500, "2008","이미 존재하는 자산입니다."),

    NOT_FOUND_ASSETS_TX(500, "3000", "등록되지 거래입니다."),

    UNAUTHORIZED(401, "8000", "인증에 실패하였습니다."),
    SESSION_INVALIDATE(401, "8001", "세션이 유효하지 않습니다."),
    ACCESS_DENIED(403, "8002", "유효하지 않은 접근입니다"),

    SERVER_ERROR(500, "9999", "서버에러"),
    ;
    private final int status;
    private final String code;
    private final String message;
}