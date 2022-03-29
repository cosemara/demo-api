package com.project.sample.api.demo.web.controller;

import com.project.sample.api.demo.common.consts.AuthConstants;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.assetsTx.AssetsTxAddRequest;
import com.project.sample.api.demo.web.model.assetsTx.AssetsTxDeleteRequest;
import com.project.sample.api.demo.web.model.assetsTx.AssetsTxUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AssetsTxControllerTest extends AbstractMockMvcTestBoilerplate {

    @Test
    @DisplayName("자산거래 리스트 조회 (유효하지 않은 헤더)")
    void getAssetsTxListInvalidHeader() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/tx/list")
                //.header(AuthConstants.AUTH_HEADER, authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_HEADER.getCode()));
    }

    @Test
    @DisplayName("자산거래 리스트 조회 (접근 거부)")
    void getAssetsTxListAuthFail() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/tx/list")
                .header(AuthConstants.AUTH_HEADER, access_denied_authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.ACCESS_DENIED.getCode()));
    }

    @Test
    @DisplayName("자산거래 리스트 조회 (세션만료)")
    void getAssetsTxListInvalidSession() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/tx/list")
                .header(AuthConstants.AUTH_HEADER, invalid_session_authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SESSION_INVALIDATE.getCode()));
    }

    @Test
    @DisplayName("자산거래 리스트 조회")
    void getAssetsTxList() throws Exception {
        RequestBuilder requestBuilder = get("/api/v1/assets/tx/list")
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("assetsType","COIN")
                .queryParam("pageNo","1");

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.assets").exists())
                .andDo(print())
                .andDo(document("getAssetsTxList",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지"),
                                fieldWithPath("assets").type(JsonFieldType.ARRAY).description("자산목록"),
                                fieldWithPath("assets.[].assetsTxId").type(JsonFieldType.NUMBER).description("자산거래번호"),
                                fieldWithPath("assets.[].assetsType").type(JsonFieldType.STRING).description("자산타입"),
                                fieldWithPath("assets.[].assetsTxType").type(JsonFieldType.STRING).description("자산거래타입"),
                                fieldWithPath("assets.[].txDate").type(JsonFieldType.STRING).description("자산거래시간"),
                                fieldWithPath("assets.[].amount").type(JsonFieldType.NUMBER).description("자산금액"),
                                fieldWithPath("pageable.pageNo").type(JsonFieldType.NUMBER).description("페이지번호"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지사이즈"),
                                fieldWithPath("pageable.totalPage").type(JsonFieldType.NUMBER).description("전체페이지"),
                                fieldWithPath("pageable.totalCnt").type(JsonFieldType.NUMBER).description("전체개수"),
                                fieldWithPath("pageable.hasMore").type(JsonFieldType.BOOLEAN).description("다음데이터 유무")

                        )
                ));
    }

    @Test
    @DisplayName("자산거래정보 추가 (유효하지 않은 파라미터)")
    void addAssetsTxNotFound() throws Exception {
        RequestBuilder requestBuilder = post("/api/v1/assets/tx/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsTxAddRequest.of("SILVER", "BUY", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.INVALID_PARAM.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("자산거래정보 추가")
    void addAssetsTx() throws Exception {
        RequestBuilder requestBuilder = post("/api/v1/assets/tx/add")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsTxAddRequest.of("GOLD", "BUY", 10000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").exists())
                .andDo(print())
                .andDo(document("addAssetsTx",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("assetsType").type(JsonFieldType.STRING).description("자산타입").optional(),
                                fieldWithPath("assetsTxType").type(JsonFieldType.STRING).description("자산거래타입").optional(),
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("자산금액").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지")
                        )
                ));
    }

    @Test
    @DisplayName("자산거래정보 수정 (존재하지 않는 자산거래내역)")
    void updAssetsTxNotFoundAssets() throws Exception {
        RequestBuilder requestBuilder = put("/api/v1/assets/tx/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsTxUpdateRequest.of(999L, 1000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.NOT_FOUND_ASSETS_TX.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("자산거래정보 수정")
    void updAssetsTx() throws Exception {
        RequestBuilder requestBuilder = put("/api/v1/assets/tx/update")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsTxUpdateRequest.of(1L, 1000)));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").exists())
                .andDo(print())
                .andDo(document("updAssetsTx",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("assetsTxId").type(JsonFieldType.NUMBER).description("거래아이디").optional(),
                                fieldWithPath("amount").type(JsonFieldType.NUMBER).description("자산금액").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지")
                        )
                ));
    }

    @Test
    @DisplayName("자산거래정보 삭제 (존재하지 않는 자산)")
    void delAssetsTxNotFoundAssets() throws Exception {
        RequestBuilder requestBuilder = delete("/api/v1/assets/tx/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsTxDeleteRequest.of(999L)));

        mvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.NOT_FOUND_ASSETS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("자산거래정보 삭제")
    void delAssetsTx() throws Exception {
        RequestBuilder requestBuilder = delete("/api/v1/assets/tx/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AuthConstants.AUTH_HEADER, authHeader)
                .content(toJson(AssetsTxDeleteRequest.of(1L)));

        mvc.perform(requestBuilder)
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ExceptionCode.SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").exists())
                .andDo(print())
                .andDo(document("delAssetsTx",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("assetsTxId").type(JsonFieldType.NUMBER).description("거래아이디").optional()
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답메시지")
                        )
                ));
    }
}