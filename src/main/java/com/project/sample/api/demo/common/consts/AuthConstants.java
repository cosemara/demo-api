package com.project.sample.api.demo.common.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthConstants {
    public static final String AUTH_HEADER = "Authorization";
    public static final String BASIC_AUTH = "Basic ";
    public static final String BEARER_AUTH  = "Bearer ";
}