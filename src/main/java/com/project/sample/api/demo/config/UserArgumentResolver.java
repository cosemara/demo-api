package com.project.sample.api.demo.config;

import com.project.sample.api.demo.common.annotation.AuthUser;
import com.project.sample.api.demo.common.consts.AuthConstants;
import com.project.sample.api.demo.common.util.JwtTokenProvider;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.repository.UserRepository;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        final boolean isAuthUserAnnotation = parameter.getParameterAnnotation(AuthUser.class) != null;
        final boolean isUserDto = parameter.getParameterType().equals(User.class);
        return isAuthUserAnnotation && isUserDto;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authorizationHeader = webRequest.getHeader(AuthConstants.AUTH_HEADER);
        if (StringUtils.isBlank(authorizationHeader)) {
            throw new CommonApiException(ExceptionCode.INVALID_HEADER);
        }

        String jwtToken = jwtTokenProvider.getTokenFromHeader(authorizationHeader, AuthConstants.BEARER_AUTH);

        jwtTokenProvider.isValidToken(jwtToken);

        String userId = jwtTokenProvider.getClaimsData(jwtToken, "email");

        UserEntity userEntity = UserEntity.builder().email(userId).build();

        log.debug("User info : {}", userEntity.getEmail());

        // 사용자 조회
        User user = checkUserExist(userEntity);
        // 사용자 탈퇴 조회
        checkUnregister(user);

        return user;
    }

    private User checkUserExist(UserEntity userEntity) {
        return userRepository.findByEmail(userEntity.getEmail()).orElseThrow(() -> new CommonApiException(ExceptionCode.USER_NOT_FOUND));
    }

    private void checkUnregister(User user) {
        if (user.isUnregister())
            throw new CommonApiException(ExceptionCode.ACCESS_DENIED);
    }
}
