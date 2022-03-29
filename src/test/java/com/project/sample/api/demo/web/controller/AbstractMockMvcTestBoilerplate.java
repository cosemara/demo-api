package com.project.sample.api.demo.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.project.sample.api.demo.common.consts.AuthConstants;
import com.project.sample.api.demo.common.util.JwtTokenProvider;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.type.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Transactional  // 테스트를 롤백 시키기위함
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class}) // (1)
public class AbstractMockMvcTestBoilerplate {

    protected MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    protected final String APPLICATION_JSON = "application/json;charset=UTF-8";
    protected final String JSON_EMPTY_STRING = "{}";

    protected String authHeader = "";
    protected String access_denied_authHeader = "";
    protected String invalid_session_authHeader = "Bearer eyJyZWdEYXRlIjoxNjQ3MjM3OTcxMDY4LCJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiUk9MRV9VU0VSIiwiZW1haWwiOiJ0ZXN0MUB0ZXN0LmNvbSIsImV4cCI6MTY0NzIzOTc3MX0.VVOSXO-TQ0CT1E5sM8ocIpCTp_DIPtOvEGVOYdDxOzYpHRqTS5CUbAcpzhX0G-q5FFSDdulhkofDPIIiq7WGxw";

    //protected final ObjectWriter objectWriter = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).writer();
    protected final ObjectWriter objectWriter = new ObjectMapper().writer();

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .apply(documentationConfiguration(restDocumentation))  // (2)
                .alwaysDo(print())
                .build();

        String token = jwtTokenProvider.generateJwtToken(User.builder().email("test1@test.com").password("1234").role(UserRole.ROLE_USER).build());
        authHeader = AuthConstants.BEARER_AUTH + token;
        token = jwtTokenProvider.generateJwtToken(User.builder().email("test3@test.com").password("1234").role(UserRole.ROLE_USER).build());
        access_denied_authHeader = AuthConstants.BEARER_AUTH + token;
    }

    protected <T> String toJson(T obj) throws JsonProcessingException {
        return objectWriter.writeValueAsString(obj);
    }
}