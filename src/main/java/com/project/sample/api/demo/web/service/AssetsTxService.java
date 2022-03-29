package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.type.AssetsTxType;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.web.model.assetsTx.*;

public interface AssetsTxService {

    AssetsTxListResponse getAssetsTxHistList(User user, AssetsTxListRequest request);

    AssetsTxAddResponse addAssetsTxHist(User user, AssetsType assetsType, AssetsTxType assetsTransType, Integer amount);

    AssetsTxUpdateResponse updateAssetsTxHist(User user, Long assetsTxId, Integer amount);

    AssetsTxDeleteResponse deleteAssetsTxHist(User user, Long assetsTxId);
}
