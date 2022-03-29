package com.project.sample.api.demo.web.model.assetsTx;

import com.project.sample.api.demo.domain.type.AssetsTxType;
import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.validate.AllowParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
public class AssetsTxListRequest {

    @AllowParams(values = {AssetsType.ASSETS_COIN, AssetsType.ASSETS_GOLD, AssetsType.ASSETS_CURRENCY, AssetsType.ASSETS_STOCK})
    private AssetsType assetsType;

    @AllowParams(values = {AssetsTxType.ASSETS_BUY, AssetsTxType.ASSETS_SELL})
    private AssetsTxType assetsTxType;

    @Min(1)
    private int pageNo;

    public AssetsTxListRequest(AssetsType assetsType, @Min(1) int pageNo) {
        this.assetsType = assetsType;
        this.pageNo = pageNo;
    }
}