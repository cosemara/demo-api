package com.project.sample.api.demo.domain.entity;

import com.project.sample.api.demo.domain.type.AssetsTxType;
import com.project.sample.api.demo.domain.type.AssetsType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "assets_tx_history")
@NoArgsConstructor
@ToString
public class AssetsTxHistory extends BaseEntity {
    @Id
    @Column(name = "trns_hist_id")
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetsType assetsType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetsTxType assetsTxType;

    @Column(nullable = false)
    @Setter
    private Integer amount;

    // Entity가 생성되어 저장될 때 시간이 자동 저장
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime txDate;

    @Builder
    public AssetsTxHistory(Long id, AssetsType assetsType, User user, AssetsTxType assetsTxType, Integer amount) {
        this.id = id;
        this.assetsType = assetsType;
        this.user = user;
        this.assetsTxType = assetsTxType;
        this.amount = amount;
    }
}