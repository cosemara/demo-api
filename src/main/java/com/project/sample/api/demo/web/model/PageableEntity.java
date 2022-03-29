package com.project.sample.api.demo.web.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageableEntity {

    private int pageNo = 1;

    private int pageSize = 0;

    private int totalPage = 0;

    private int totalCnt = 0;

    private boolean hasMore = false;

    @Builder
    public PageableEntity(int pageNo, int pageSize, int totalPage, int totalCnt, boolean hasMore) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCnt = totalCnt;
        this.hasMore = hasMore;
    }
}
