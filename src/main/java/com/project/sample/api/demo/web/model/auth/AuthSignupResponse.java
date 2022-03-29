package com.project.sample.api.demo.web.model.auth;

import com.project.sample.api.demo.common.model.CommonResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthSignupResponse extends CommonResponse {
    @Builder
    public AuthSignupResponse(String code, String message) {
        super(code, message);
    }
}
