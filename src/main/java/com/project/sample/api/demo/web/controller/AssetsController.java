package com.project.sample.api.demo.web.controller;

import com.project.sample.api.demo.common.annotation.AuthUser;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.assets.*;
import com.project.sample.api.demo.web.service.AssetsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assets/")
public class AssetsController {

    private final AssetsService assetsService;

    @GetMapping("list")
    public AssetsListResponse getAssetsList(@AuthUser User user) {
        return assetsService.getAssetsList(user);
    }

    @PostMapping("add")
    public AssetsAddResponse addAssets(@AuthUser User user, @Valid @RequestBody AssetsAddRequest assetsAddRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        // 2. 비지니스 로직
        return assetsService.addAssets(user, AssetsType.valueOf(assetsAddRequest.getAssetsType()), assetsAddRequest.getAmount());
    }

    @PutMapping("update")
    public AssetsUpdateResponse updateAssets(@AuthUser User user, @Valid @RequestBody AssetsUpdateRequest assetsUpdateRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        // 2. 비지니스 로직
        return assetsService.updateAssets(user, AssetsType.valueOf(assetsUpdateRequest.getAssetsType()), assetsUpdateRequest.getAmount());
    }

    @DeleteMapping("delete")
    public AssetsDeleteResponse deleteAssets(@AuthUser User user, @Valid @RequestBody AssetsDeleteRequest assetsDeleteRequest, BindingResult bindingResult) throws Exception {

        // 1. 파라미터 검증
        if (bindingResult.hasErrors()) {
            log.debug("Error!!:{}", bindingResult.toString());
            throw new CommonApiException(ExceptionCode.INVALID_PARAM);
        }

        // 2. 비지니스 로직
        return assetsService.deleteAssets(user, AssetsType.valueOf(assetsDeleteRequest.getAssetsType()));
    }
}