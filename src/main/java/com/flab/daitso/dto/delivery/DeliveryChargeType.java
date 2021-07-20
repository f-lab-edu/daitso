package com.flab.daitso.dto.delivery;

public enum DeliveryChargeType {
    FREE("무료배송"),
    NOT_FREE("유료배송"),
    CONDITIONAL_FREE("조건부 무료배송");

    private String status;

    DeliveryChargeType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
