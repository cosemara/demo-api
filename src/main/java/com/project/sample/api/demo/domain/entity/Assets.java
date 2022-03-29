package com.project.sample.api.demo.domain.entity;

import com.project.sample.api.demo.domain.type.AssetsType;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "assets")
@NoArgsConstructor
@ToString
public class Assets extends BaseEntity {
    @Id
    @Column(name = "assets_id")
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssetsType assetsType;

    @Column(nullable = false)
    @Setter
    private Integer amount;

    @Builder
    public Assets(Long id, User user, AssetsType assetsType, Integer amount) {
        this.id = id;
        this.user = user;
        this.assetsType = assetsType;
        this.amount = amount;
    }
}