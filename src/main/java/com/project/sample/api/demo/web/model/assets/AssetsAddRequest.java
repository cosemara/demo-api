package com.project.sample.api.demo.web.model.assets;

import com.project.sample.api.demo.domain.type.AssetsType;
import com.project.sample.api.demo.validate.AllowParams;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Digits;

@Getter
@Setter
@ToString
public class AssetsAddRequest {

    @AllowParams(values = {AssetsType.ASSETS_COIN, AssetsType.ASSETS_GOLD, AssetsType.ASSETS_CURRENCY, AssetsType.ASSETS_STOCK})
    private String assetsType;

    @Digits(integer = 10, fraction = 0)
    private Integer amount;

    public AssetsAddRequest() {
    }

    public static AssetsAddRequest of(String assetsType, Integer amount) {
        AssetsAddRequest request = new AssetsAddRequest();
        request.assetsType = assetsType;
        request.amount = amount;
        return request;
    }
}
