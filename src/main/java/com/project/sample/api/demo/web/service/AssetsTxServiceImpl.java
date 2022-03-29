package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.common.consts.CommonConstants;
import com.project.sample.api.demo.domain.entity.AssetsTxHistory;
import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.repository.AssetsTxHistRepository;
import com.project.sample.api.demo.domain.repository.AssetsTxHistRepositorySurpport;
import com.project.sample.api.demo.domain.type.AssetsTxType;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import com.project.sample.api.demo.web.model.PageableEntity;
import com.project.sample.api.demo.web.model.assetsTx.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AssetsTxServiceImpl implements AssetsTxService {

    private final AssetsTxHistRepository assetsTxHistRepository;
    private final AssetsTxHistRepositorySurpport assetsTxHistRepositorySurpport;

    @Override
    public AssetsTxListResponse getAssetsTxHistList(User user, AssetsTxListRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNo()-1, CommonConstants.PAGING_DEFAULT_SIZE, Sort.Direction.DESC, "id");
        PageImpl<AssetsTxHistory> assetsTxHistListPage;

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime before30Days = today.minusDays(30);

        if (request.getAssetsType() != null) {
            assetsTxHistListPage = assetsTxHistRepositorySurpport.findAllByUserIdAndAssetsTypeAndAssetsTxType(user.getId(), request.getAssetsType(), request.getAssetsTxType(), before30Days, today, pageable);
        } else {
            assetsTxHistListPage = assetsTxHistRepositorySurpport.findAllByUserId(user.getId(), pageable);
        }

        List<AssetsTxHistory> assetsTxHistList = assetsTxHistListPage.getContent();

        List<AssetsTxEntity> list = assetsTxHistList.stream().map(v -> new AssetsTxEntity(v.getId(), v.getAssetsType().name(), v.getAssetsTxType().name(), v.getAmount(), v.getTxDate())).collect(Collectors.toList());

        // 4. 페이징 정보 세팅
        PageableEntity pageableEntity = PageableEntity.builder()
                .pageNo(assetsTxHistListPage.getNumber() +1)
                .pageSize(assetsTxHistListPage.getSize())
                .totalPage(assetsTxHistListPage.getTotalPages())
                .totalCnt((int) assetsTxHistListPage.getTotalElements())
                .hasMore(assetsTxHistListPage.hasNext())
                .build();

        return AssetsTxListResponse.builder()
                .code(ExceptionCode.SUCCESS.getCode())
                .message(ExceptionCode.SUCCESS.getMessage())
                .assets(list)
                .pageable(pageableEntity)
                .build();
    }

    @Override
    public AssetsTxAddResponse addAssetsTxHist(User user, AssetsType assetsType, AssetsTxType assetsTxType, Integer amount) {

        try {
            assetsTxHistRepository.save(AssetsTxHistory.builder()
                                        .user(user)
                                        .assetsType(assetsType)
                                        .assetsTxType(assetsTxType)
                                        .amount(amount)
                                        .build());

            return AssetsTxAddResponse.builder()
                    .code(ExceptionCode.SUCCESS.getCode())
                    .message(ExceptionCode.SUCCESS.getMessage())
                    .build();

        } catch (Exception e) {
            log.error("거래내역 등록 실패 : {}", e);
            throw new CommonApiException(ExceptionCode.FAIL_ASSETS_ADD);
        }
    }

    @Override
    public AssetsTxUpdateResponse updateAssetsTxHist(User user, Long assetsTxId, Integer amount) {

        AssetsTxHistory updateAssetsTxHist = assetsTxHistRepository.findById(assetsTxId).orElseThrow(() -> new CommonApiException(ExceptionCode.NOT_FOUND_ASSETS_TX));

        try {
            updateAssetsTxHist.setAmount(amount);
            assetsTxHistRepository.save(updateAssetsTxHist);

            return AssetsTxUpdateResponse.builder()
                    .code(ExceptionCode.SUCCESS.getCode())
                    .message(ExceptionCode.SUCCESS.getMessage())
                    .build();

        } catch (Exception e) {
            log.error("거래내역 수정 실패 : {}", e);
            throw new CommonApiException(ExceptionCode.FAIL_ASSETS_UPD);
        }
    }

    @Override
    public AssetsTxDeleteResponse deleteAssetsTxHist(User user, Long assetsTxId) {

        AssetsTxHistory deleteAssetsTxHist = assetsTxHistRepository.findById(assetsTxId).orElseThrow(() -> new CommonApiException(ExceptionCode.NOT_FOUND_ASSETS));

        try {
            assetsTxHistRepository.delete(deleteAssetsTxHist);

            return AssetsTxDeleteResponse.builder()
                    .code(ExceptionCode.SUCCESS.getCode())
                    .message(ExceptionCode.SUCCESS.getMessage())
                    .build();
        } catch (Exception e) {
            log.error("거래내역 삭제 실패 : {}", e);
            throw new CommonApiException(ExceptionCode.FAIL_ASSETS_DEL);
        }

    }
}
