package com.project.sample.api.demo.web.model.assetsTx;

import com.project.sample.api.demo.common.model.CommonResponse;
import com.project.sample.api.demo.web.model.PageableEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AssetsTxListResponse extends CommonResponse {

    private List<AssetsTxEntity> assets;

    private PageableEntity pageable;

    @Builder
    public AssetsTxListResponse(String code, String message, List<AssetsTxEntity> assets, PageableEntity pageable) {
        super(code, message);
        this.assets = assets;
        this.pageable = pageable;
    }
}
