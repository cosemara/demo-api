package com.project.sample.api.demo.web.controller;

import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.auth.AuthSigninRequest;
import com.project.sample.api.demo.web.model.auth.AuthSigninResponse;
import com.project.sample.api.demo.web.model.auth.AuthSignupRequest;
import com.project.sample.api.demo.web.model.auth.AuthSignupResponse;
import com.project.sample.api.demo.web.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/")
public class AuthController {

    private final AuthService authService;

    @PostMapping("signin")
    public AuthSigninResponse signin(@Valid @RequestBody AuthSigninRequest authSigninRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        return authService.signin(authSigninRequest);
    }

    @PostMapping("signup")
    public AuthSignupResponse signup(@Valid @RequestBody AuthSignupRequest authSignupRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        return authService.signup(authSignupRequest);
    }
}