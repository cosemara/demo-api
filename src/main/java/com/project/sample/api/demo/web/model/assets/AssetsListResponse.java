package com.project.sample.api.demo.web.model.assets;

import com.project.sample.api.demo.common.model.CommonResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class AssetsListResponse extends CommonResponse {

    private List<AssetsEntity> assets;

    @Builder
    public AssetsListResponse(String code, String message, List<AssetsEntity> assets) {
        super(code, message);
        this.assets = assets;
    }
}
