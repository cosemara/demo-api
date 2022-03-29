package com.project.sample.api.demo.web.model.assetsTx;

import com.project.sample.api.demo.common.model.CommonResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssetsTxUpdateResponse extends CommonResponse {
    @Builder
    public AssetsTxUpdateResponse(String code, String message) {
        super(code, message);
    }
}
