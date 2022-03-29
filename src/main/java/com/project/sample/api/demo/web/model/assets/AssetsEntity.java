package com.project.sample.api.demo.web.model.assets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetsEntity {
    private String assetsType;
    private Integer amount;

    public AssetsEntity(String assetsType, Integer amount) {
        this.assetsType = assetsType;
        this.amount = amount;
    }
}
