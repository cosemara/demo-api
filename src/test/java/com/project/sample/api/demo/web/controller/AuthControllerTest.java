package com.project.sample.api.demo.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.sample.api.demo.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest extends AbstractMockMvcTestBoilerplate {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입을 처리한다. (필수 파라미터 오류)")
    void signupParamException() throws Exception {
        Map<String, String> input = new HashMap<>();
        //input.put("username","김두한");
        input.put("email","test@test.com");
        input.put("password","1234");
        input.put("role","ROLE_USER");

        RequestBuilder requestBuilder = post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_PARAM.getCode()));
    }

    @Test
    @DisplayName("회원가입을 처리한다. (필수 파라미터 오류, 이메일 형식 오류)")
    void signupParamExceptionEmail() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("username","김두한");
        input.put("email","test!test.com");
        input.put("password","1234");
        input.put("role","ROLE_USER");

        RequestBuilder requestBuilder = post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_PARAM.getCode()));
    }

    @Test
    @DisplayName("회원가입을 처리한다. (필수 파라미터 오류, 허용되지 않은 타입)")
    void signupNotAllowedParamException() throws Exception {
        Map<String, String> input = new HashMap<>();
        input.put("username","김두한");
        input.put("email","test!test.com");
        input.put("password","1234");
        input.put("role","ROLE_USER1");

        RequestBuilder requestBuilder = post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_PARAM.getCode()));
    }

    @Test
    @DisplayName("회원가입을 처리한다.")
    void signup() throws Exception {

        Map<String, String> input = new HashMap<>();
        input.put("username","김두한");
        input.put("email","test@test.com");
        input.put("password","1234");
        input.put("role","ROLE_USER");

        RequestBuilder requestBuilder = post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andDo(print())
                .andDo(document("signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("이름").optional(),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").optional(),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional(),
                                fieldWithPath("role").type(JsonFieldType.STRING).description("회원권한").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지")
                        )
                ));
    }

    @Test
    @DisplayName("회원 로그인을 처리한다.")
    void signin() throws Exception {

        Map<String, String> input = new HashMap<>();
        input.put("email","test1@test.com");
        input.put("password","1234");

        RequestBuilder requestBuilder = post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.accessToken").exists())
                .andDo(print())
                .andDo(document("signin",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").optional(),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("roles").type(JsonFieldType.ARRAY).description("회원권한"),
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스토큰"),
                                fieldWithPath("tokenType").type(JsonFieldType.STRING).description("토큰유형")
                        )
                ));
    }

    @Test
    @DisplayName("회원 로그인을 처리한다. (필수 파라미터 오류)")
    void signinParamException() throws Exception {

        Map<String, String> input = new HashMap<>();
        //input.put("email","test1@test.com");
        input.put("password","1234");

        RequestBuilder requestBuilder = post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_PARAM.getCode()));
    }

    @Test
    @DisplayName("회원 로그인을 처리한다. (필수 파라미터 오류, 이메일 형식 오류)")
    void signinParamExceptionEmail() throws Exception {

        Map<String, String> input = new HashMap<>();
        //input.put("email","test1@test.com");
        input.put("password","1234");

        RequestBuilder requestBuilder = post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_PARAM.getCode()));
    }

    @Test
    @DisplayName("회원 로그인을 처리한다. (사용자를 찾을 수 없음)")
    void signinUserNotFound() throws Exception {

        Map<String, String> input = new HashMap<>();
        input.put("email","test119@test.com");
        input.put("password","1234");

        RequestBuilder requestBuilder = post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.USER_NOT_FOUND.getCode()));
    }

    @Test
    @DisplayName("회원 로그인을 처리한다. (인증실패)")
    void signinUserPasswordFail() throws Exception {

        Map<String, String> input = new HashMap<>();
        input.put("email","test1@test.com");
        input.put("password","12345");

        RequestBuilder requestBuilder = post("/api/v1/auth/signin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsBytes(input));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.UNAUTHORIZED.getCode()));
    }
}