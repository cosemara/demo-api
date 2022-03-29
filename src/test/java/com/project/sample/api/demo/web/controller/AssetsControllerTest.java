package com.project.sample.api.demo.web.controller;

import com.project.sample.api.demo.common.consts.AuthConstants;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.assets.AssetsAddRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AssetsControllerTest extends AbstractMockMvcTestBoilerplate {

    @Test
    @DisplayName("자산 리스트 조회 (유효하지 않은 헤더)")
    void getAssetsListInvalidHeader() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/list")
                //.header(AuthConstants.AUTH_HEADER, authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_HEADER.getCode()));
    }

    @Test
    @DisplayName("자산 리스트 조회 (접근 거부)")
    void getAssetsListAuthFail() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/list")
                .header(AuthConstants.AUTH_HEADER, access_denied_authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.ACCESS_DENIED.getCode()));
    }

    @Test
    @DisplayName("자산 리스트 조회 (세션만료)")
    void getAssetsListInvalidSession() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/list")
                .header(AuthConstants.AUTH_HEADER, invalid_session_authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SESSION_INVALIDATE.getCode()));
    }

    @Test
    @DisplayName("자산 리스트 조회")
    void getAssetsList() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/list")
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.assets").exists())
                .andDo(print())
                .andDo(document("getAssetsList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지"),
                                fieldWithPath("assets").type(JsonFieldType.ARRAY).description("자산목록"),
                                fieldWithPath("assets.[].assetsType").type(JsonFieldType.STRING).description("자산타입"),
                                fieldWithPath("assets.[].amount").type(JsonFieldType.NUMBER).description("자산금액")
                        )
                ));
    }

    @Test
    @DisplayName("자산정보 추가 (유효하지 않은 파라미터)")
    void addAssetsNotFound() throws Exception {
        RequestBuilder requestBuilder = post("/api/v1/assets/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsAddRequest.of("SILVER", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_PARAM.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("자산정보 추가 (이미 등록된 자산 정보)")
    void addAssetsAlreadyExist() throws Exception {
        RequestBuilder requestBuilder = post("/api/v1/assets/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsAddRequest.of("COIN", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.ALREADY_EXIST_ASSETS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("자산정보 추가")
    void addAssets() throws Exception {
        RequestBuilder requestBuilder = post("/api/v1/assets/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsAddRequest.of("GOLD", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").exists())
                .andDo(print())
                .andDo(document("addAssets",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("assetsType").type(JsonFieldType.STRING).description("자산타입").optional(),
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("자산금액").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지")
                        )
                ));
    }

    @Test
    @DisplayName("자산정보 수정 (존재하지 않는 자산)")
    void updAssetsNotFoundAssets() throws Exception {
        RequestBuilder requestBuilder = put("/api/v1/assets/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsAddRequest.of("GOLD", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.NOT_FOUND_ASSETS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("자산정보 수정")
    void updAssets() throws Exception {
        RequestBuilder requestBuilder = put("/api/v1/assets/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsAddRequest.of("COIN", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").exists())
                .andDo(print())
                .andDo(document("updAssets",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("assetsType").type(JsonFieldType.STRING).description("자산타입").optional(),
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("자산금액").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지")
                        )
                ));
    }

    @Test
    @DisplayName("자산정보 삭제 (존재하지 않는 자산)")
    void delAssetsNotFoundAssets() throws Exception {
        RequestBuilder requestBuilder = delete("/api/v1/assets/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsAddRequest.of("GOLD", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.NOT_FOUND_ASSETS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("자산정보 삭제")
    void delAssets() throws Exception {
        RequestBuilder requestBuilder = delete("/api/v1/assets/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsAddRequest.of("COIN", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").exists())
                .andDo(print())
                .andDo(document("delAssets",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("assetsType").type(JsonFieldType.STRING).description("자산타입").optional(),
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("자산금액").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지")
                        )
                ));
    }
}