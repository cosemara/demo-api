package com.project.sample.api.demo.web.model.auth;

import com.project.sample.api.demo.common.model.CommonResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AuthSigninResponse extends CommonResponse {

    private String email;
    private List<String> roles;
    private String accessToken;
    private String tokenType;

    @Builder
    public AuthSigninResponse(String code, String message, String email, List<String> roles, String accessToken, String tokenType) {
        super(code, message);
        this.email = email;
        this.roles = roles;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
