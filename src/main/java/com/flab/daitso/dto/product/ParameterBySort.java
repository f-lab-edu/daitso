package com.flab.daitso.dto.product;

public class ParameterBySort {
    private Long categoryId;
    private int page;
    private int listSize;

    public ParameterBySort(Long categoryId, int page, int listSize) {
        this.categoryId = categoryId;
        this.page = page;
        this.listSize = listSize;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public int getPage() {
        return page;
    }

    public int getListSize() {
        return listSize;
    }
}
