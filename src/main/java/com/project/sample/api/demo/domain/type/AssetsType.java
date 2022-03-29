package com.project.sample.api.demo.domain.type;

public enum AssetsType {
    COIN("COIN"), GOLD("GOLD"), STOCK("STOCK"), CURRENCY("CURRENCY");

    private final String code;

    public static final String ASSETS_COIN = "COIN";
    public static final String ASSETS_GOLD = "GOLD";
    public static final String ASSETS_STOCK = "STOCK";
    public static final String ASSETS_CURRENCY = "CURRENCY";

    AssetsType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
