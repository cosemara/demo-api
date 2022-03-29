package com.project.sample.api.demo.web.model.assetsTx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AssetsTxDeleteRequest {

    private Long assetsTxId;

    public AssetsTxDeleteRequest() {
    }

    public static AssetsTxDeleteRequest of(Long assetsTxId) {
        AssetsTxDeleteRequest request = new AssetsTxDeleteRequest();
        request.assetsTxId = assetsTxId;
        return request;
    }
}
