package com.project.sample.api.demo.web.model.assetsTx;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssetsTxEntity {
    private Long assetsTxId;

    private String assetsType;

    private String assetsTxType;

    private Integer amount;

    @JsonFormat
    private LocalDateTime txDate;

    public AssetsTxEntity(Long assetsTxId, String assetsType, String assetsTxType, Integer amount, LocalDateTime txDate) {
        this.assetsTxId = assetsTxId;
        this.assetsType = assetsType;
        this.assetsTxType = assetsTxType;
        this.amount = amount;
        this.txDate = txDate;
    }
}