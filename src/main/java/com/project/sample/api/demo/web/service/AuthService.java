package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.web.model.auth.AuthSigninRequest;
import com.project.sample.api.demo.web.model.auth.AuthSigninResponse;
import com.project.sample.api.demo.web.model.auth.AuthSignupRequest;
import com.project.sample.api.demo.web.model.auth.AuthSignupResponse;

public interface AuthService {
    AuthSigninResponse signin(AuthSigninRequest authSigninRequest);
    AuthSignupResponse signup(AuthSignupRequest authSignupRequest);
}
