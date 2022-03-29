package com.project.sample.api.demo.web.model.assets;

import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.validate.AllowParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssetsDeleteRequest {

    @AllowParams(values = {AssetsType.ASSETS_COIN, AssetsType.ASSETS_GOLD, AssetsType.ASSETS_CURRENCY, AssetsType.ASSETS_STOCK})
    private String assetsType;

    public AssetsDeleteRequest() {
    }

    public static AssetsDeleteRequest of(String assetsType) {
        AssetsDeleteRequest request = new AssetsDeleteRequest();
        request.assetsType = assetsType;
        return request;
    }
}
