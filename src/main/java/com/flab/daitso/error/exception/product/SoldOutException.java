package com.flab.daitso.error.exception.product;

//  품절 상태를 나타내기 위한 에외 클래스
public class SoldOutException extends RuntimeException {

    public SoldOutException() {
        super("품절된 상품입니다.");
    }
}
