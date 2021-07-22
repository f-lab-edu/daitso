package com.flab.daitso.dto.inqury;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class InquiryResponse {

    private final int inquiryType; // 문의 유형

    private final int productId; // 상품 아이디

    private final String content; // 문의 내용

    private final Map<Integer, InquiryType> typeTranslator = Map.of( // DB 에 int 로 저장된 문의 유형을 enum 으로 변환 위한 맵핑
            0, InquiryType.ORDERED_PRODUCT,
            1, InquiryType.DELIVERY,
            2, InquiryType.SERVICE_SATISFACTION,
            3, InquiryType.ERROR_REPORT
    );

    public InquiryResponse(int inquiryType, int productId, String content) {
        this.inquiryType = inquiryType;
        this.productId = productId;
        this.content = content;
    }

    public InquiryType getInquiryType() {
        return typeTranslator.get(inquiryType);  // DB 에 int 로 저장된 문의 유형을 enum 으로 변환해 클라이언트에 리턴
    }

    public int getProductId() {
        return productId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "InquiryResponse{" +
                "inquiryType=" + inquiryType +
                ", productId=" + productId +
                ", content='" + content + '\'' +
                '}';
    }
}
