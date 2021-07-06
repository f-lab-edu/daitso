package com.flab.daitso.dto.user;

public enum PaymentOption {

    ACCOUNT_TRANSFER(1),
    PHONE_BILL(2),
    CREDIT_CARD(3);

    private final int option;

    PaymentOption(int option){
        this.option = option;
    }

    public int getOption() {
        return option;
    }
}
