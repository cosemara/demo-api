package com.project.sample.api.demo.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {
    String code;
    String message;

    public CommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
