package com.project.sample.api.demo.web.service;

import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.web.model.assets.AssetsAddResponse;
import com.project.sample.api.demo.web.model.assets.AssetsDeleteResponse;
import com.project.sample.api.demo.web.model.assets.AssetsListResponse;
import com.project.sample.api.demo.web.model.assets.AssetsUpdateResponse;

public interface AssetsService {

    AssetsListResponse getAssetsList(User user);

    AssetsAddResponse addAssets(User user, AssetsType assetsType, Integer amount);

    AssetsUpdateResponse updateAssets(User user, AssetsType assetsType, Integer amount);

    AssetsDeleteResponse deleteAssets(User user, AssetsType assetsType);
}
