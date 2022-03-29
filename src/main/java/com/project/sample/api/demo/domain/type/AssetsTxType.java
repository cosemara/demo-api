package com.project.sample.api.demo.domain.type;

public enum AssetsTxType {
    BUY(AssetsTxType.ASSETS_BUY), SELL(AssetsTxType.ASSETS_SELL);

    private final String code;

    public static final String ASSETS_BUY = "BUY";
    public static final String ASSETS_SELL = "SELL";

    AssetsTxType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
