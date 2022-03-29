package com.project.sample.api.demo.web.model.assetsTx;

import com.project.sample.api.demo.domain.type.AssetsTxType;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.validate.AllowParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;

@Getter
@Setter
@ToString
public class AssetsTxAddRequest {

    @AllowParams(values = {AssetsType.ASSETS_COIN, AssetsType.ASSETS_GOLD, AssetsType.ASSETS_CURRENCY, AssetsType.ASSETS_STOCK})
    private String assetsType;

    @AllowParams(values = {AssetsTxType.ASSETS_BUY, AssetsTxType.ASSETS_SELL})
    private String assetsTxType;

    @Digits(integer = 10, fraction = 0)
    private Integer amount;

    public AssetsTxAddRequest() {
    }

    public static AssetsTxAddRequest of(String assetsType, String assetsTxType, Integer amount) {
        AssetsTxAddRequest request = new AssetsTxAddRequest();
        request.assetsType = assetsType;
        request.assetsTxType = assetsTxType;
        request.amount = amount;
        return request;
    }
}
