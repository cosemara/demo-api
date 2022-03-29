package com.project.sample.api.demo.web.model.assets;

import com.project.sample.api.demo.common.model.CommonResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AssetsUpdateResponse extends CommonResponse {
    @Builder
    public AssetsUpdateResponse(String code, String message) {
        super(code, message);
    }
}
