package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.common.util.JwtTokenProvider;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.repository.UserRepository;
import com.project.sample.api.demo.domain.type.UserRole;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.auth.AuthSigninRequest;
import com.project.sample.api.demo.web.model.auth.AuthSigninResponse;
import com.project.sample.api.demo.web.model.auth.AuthSignupRequest;
import com.project.sample.api.demo.web.model.auth.AuthSignupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    UserDetailServiceImpl userDetailService;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    PasswordEncoder encoder;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        User user = User.builder().id(1L).username("test").email("test@test1.com").password("1234").role(UserRole.ROLE_USER).build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userDetailService.authenticateByEmailAndPassword(any(), any())).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(User.builder().id(1L).username("test").email("test").password("1234").role(UserRole.ROLE_USER).build());
        when(jwtTokenProvider.generateJwtToken(user)).thenReturn("1234");
    }

    @Test
    @DisplayName("회원 로그인을 처리한다.")
    void signin() {
        AuthSigninResponse response = authService.signin(AuthSigninRequest.builder().email("test@test1.com").password("1234").build());
        assertThat(response.getCode()).isEqualTo(ExceptionCode.SUCCESS.getCode());
        assertThat(response.getAccessToken()).isNotBlank();
    }
    
    @Test
    @DisplayName("회원가입을 처리한다.")
    void signup() {
        AuthSignupResponse response = authService.signup(AuthSignupRequest.builder().username("test123").password("1234").email("test123@test.com").role(UserRole.ROLE_USER.name()).build());
        assertThat(response.getCode()).isEqualTo(ExceptionCode.SUCCESS.getCode());
    }
}