package com.project.sample.api.demo.web.model.auth;

import com.project.sample.api.demo.domain.type.UserRole;
import com.project.sample.api.demo.validate.AllowParams;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AuthSignupRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @AllowParams(values = {UserRole.USERROLE_ADMIN, UserRole.USERROLE_USER})
    private String role;

    public AuthSignupRequest() {
    }

    @Builder
    public AuthSignupRequest(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
