package com.flab.daitso.dto.inqury;

public enum InquiryType {

    ORDERED_PRODUCT(0), // 주문 상품 문의
    DELIVERY(1), // 배송 문의
    SERVICE_SATISFACTION(2), // 서비스 칭찬
    ERROR_REPORT(3); // 오류 제보

    private final int type;

    InquiryType(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
