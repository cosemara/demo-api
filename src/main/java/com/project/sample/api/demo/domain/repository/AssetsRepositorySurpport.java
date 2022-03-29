package com.project.sample.api.demo.domain.repository;

import com.project.sample.api.demo.domain.entity.Assets;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class AssetsRepositorySurpport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public AssetsRepositorySurpport(JPAQueryFactory jpaQueryFactory) {
        super(Assets.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


}
