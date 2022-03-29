package com.project.sample.api.demo.web.model.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AuthSigninRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public AuthSigninRequest() {
    }

    @Builder
    public AuthSigninRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
