package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.domain.entity.Assets;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.repository.AssetsRepository;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.assets.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AssetsServiceImpl implements AssetsService {

    private final AssetsRepository assetsRepository;

    @Override
    public AssetsListResponse getAssetsList(User user) {
        List<Assets> assetsList = assetsRepository.findByUserId(user.getId());

        List<AssetsEntity> list = assetsList.stream().map(v -> new AssetsEntity(v.getAssetsType().name(), v.getAmount())).collect(Collectors.toList());

        return AssetsListResponse.builder()
                .code(ExceptionCode.SUCCESS.getCode())
                .message(ExceptionCode.SUCCESS.getMessage())
                .assets(list)
                .build();
    }

    @Override
    public AssetsAddResponse addAssets(User user, AssetsType assetsType, Integer amount) {

        Optional<Assets> optionalAssets = assetsRepository.findByUserIdAndAssetsType(user.getId(), assetsType);
        if (optionalAssets.isPresent()) {
            throw new CommonApiException(ExceptionCode.ALREADY_EXIST_ASSETS);
        }

        try {
            assetsRepository.save(Assets.builder()
                                            .user(user)
                                            .assetsType(assetsType)
                                            .amount(amount)
                                            .build());

            return AssetsAddResponse.builder()
                    .code(ExceptionCode.SUCCESS.getCode())
                    .message(ExceptionCode.SUCCESS.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("자산등록 실패 : {}", e);
            throw new CommonApiException(ExceptionCode.FAIL_ASSETS_ADD);
        }
    }

    @Override
    public AssetsUpdateResponse updateAssets(User user, AssetsType assetsType, Integer amount) {

        Assets updateAssets = assetsRepository.findByUserIdAndAssetsType(user.getId(), assetsType).orElseThrow(() -> new CommonApiException(ExceptionCode.NOT_FOUND_ASSETS));

        try {
            updateAssets.setAmount(amount);
            assetsRepository.save(updateAssets);

            return AssetsUpdateResponse.builder()
                    .code(ExceptionCode.SUCCESS.getCode())
                    .message(ExceptionCode.SUCCESS.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("자산수정 실패 : {}", e);
            throw new CommonApiException(ExceptionCode.FAIL_ASSETS_UPD);
        }
    }

    @Override
    public AssetsDeleteResponse deleteAssets(User user, AssetsType assetsType) {

        Assets updateAssets = assetsRepository.findByUserIdAndAssetsType(user.getId(), assetsType).orElseThrow(() -> new CommonApiException(ExceptionCode.NOT_FOUND_ASSETS));

        try {
            assetsRepository.delete(updateAssets);

            return AssetsDeleteResponse.builder()
                    .code(ExceptionCode.SUCCESS.getCode())
                    .message(ExceptionCode.SUCCESS.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("자산삭제 실패 : {}", e);
            throw new CommonApiException(ExceptionCode.FAIL_ASSETS_DEL);
        }

    }
}
