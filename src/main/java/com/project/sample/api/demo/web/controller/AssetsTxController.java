package com.project.sample.api.demo.web.controller;

import com.project.sample.api.demo.common.annotation.AuthUser;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.type.AssetsTxType;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.assetsTx.*;
import com.project.sample.api.demo.web.service.AssetsTxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assets/tx/")
public class AssetsTxController {

    private final AssetsTxService assetsTxService;

    @GetMapping("list")
    public AssetsTxListResponse getAssetsTxList(@AuthUser User user, AssetsTxListRequest assetsTxListRequest, BindingResult bindingResult) {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        return assetsTxService.getAssetsTxHistList(user, assetsTxListRequest);
    }

    @PostMapping("add")
    public AssetsTxAddResponse addAssetsTxHist(@AuthUser User user, @Valid @RequestBody AssetsTxAddRequest assetsTxAddRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        // 2. 비지니스 로직
        return assetsTxService.addAssetsTxHist(user,
                AssetsType.valueOf(assetsTxAddRequest.getAssetsType()),
                AssetsTxType.valueOf(assetsTxAddRequest.getAssetsTxType()),
                assetsTxAddRequest.getAmount());
    }

    @PutMapping("update")
    public AssetsTxUpdateResponse updateAssetsTxHist(@AuthUser User user, @Valid @RequestBody AssetsTxUpdateRequest assetsTxUpdateRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        // 2. 비지니스 로직
        return assetsTxService.updateAssetsTxHist(user, assetsTxUpdateRequest.getAssetsTxId(), assetsTxUpdateRequest.getAmount());
    }

    @DeleteMapping("delete")
    public AssetsTxDeleteResponse deleteAssetsTxHist(@AuthUser User user, @Valid @RequestBody AssetsTxDeleteRequest assetsTxDeleteRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        // 2. 비지니스 로직
        assetsTxService.deleteAssetsTxHist(user, assetsTxDeleteRequest.getAssetsTxId());

        return AssetsTxDeleteResponse.builder()
                .code(ExceptionCode.SUCCESS.getCode())
                .message(ExceptionCode.SUCCESS.getMessage())
                .build();
    }
}