package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.common.consts.AuthConstants;
import com.project.sample.api.demo.common.util.JwtTokenProvider;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.repository.UserRepository;
import com.project.sample.api.demo.domain.type.UserRole;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.auth.AuthSigninRequest;
import com.project.sample.api.demo.web.model.auth.AuthSigninResponse;
import com.project.sample.api.demo.web.model.auth.AuthSignupRequest;
import com.project.sample.api.demo.web.model.auth.AuthSignupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailServiceImpl userDetailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public AuthSigninResponse signin(AuthSigninRequest authSigninRequest) {

        String email = authSigninRequest.getEmail();
        String password = authSigninRequest.getPassword();
        User user = userDetailService.authenticateByEmailAndPassword(email, password);
        String token = jwtTokenProvider.generateJwtToken(user);
        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        return AuthSigninResponse.builder()
                .code(ExceptionCode.SUCCESS.getCode())
                .message(ExceptionCode.SUCCESS.getMessage())
                .email(email)
                .roles(roles)
                .accessToken(token)
                .tokenType(AuthConstants.BEARER_AUTH)
                .build();
    }

    @Override
    public AuthSignupResponse signup(AuthSignupRequest authSignupRequest) {

        // 이메일 중복체크
        if (userRepository.existsByEmail(authSignupRequest.getEmail())) {
            throw new CommonApiException(ExceptionCode.ALREADY_EXIST_EMAIL);
        }

        User signupUser = User.builder()
                .username(authSignupRequest.getUsername())
                .email(authSignupRequest.getEmail())
                .password(encoder.encode(authSignupRequest.getPassword()))
                .role(UserRole.valueOf(authSignupRequest.getRole()))
                .build();

        userRepository.save(signupUser);

        return AuthSignupResponse.builder()
                .code(ExceptionCode.SUCCESS.getCode())
                .message(ExceptionCode.SUCCESS.getMessage())
                .build();
    }
}
