package com.project.sample.api.demo.web.model.assetsTx;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class AssetsTxUpdateRequest {

    private Long assetsTxId;

    @Digits(integer = 10, fraction = 0)
    private Integer amount;

    public AssetsTxUpdateRequest() {
    }

    public static AssetsTxUpdateRequest of(Long assetsTxId, Integer amount) {
        AssetsTxUpdateRequest request = new AssetsTxUpdateRequest();
        request.assetsTxId = assetsTxId;
        request.amount = amount;
        return request;
    }
}
