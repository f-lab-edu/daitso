package com.flab.daitso.dto.inqury;

import javax.validation.constraints.NotNull;

public class InquiryRequest {

    private final InquiryType inquiryType; // 문의 유형

    private final int productId; // 상품 아이디

    @NotNull(message = "문의 내용을 적어주세요")
    private final String content; // 문의 내용

    public InquiryRequest(InquiryType inquiryType, int productId, String content) {
        this.inquiryType = inquiryType;
        this.productId = productId;
        this.content = content;
    }

    public int getInquiryType() {
        return inquiryType.getType();
    }  // DB 에 Enum value 저장

    public int getProductId() {
        return productId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Inquiry{" +
                "inquiryType=" + inquiryType +
                ", productId=" + productId +
                ", content='" + content + '\'' +
                '}';
    }
}
