package com.flab.daitso.error.exception.product;

public class DuplicateProductNameException extends RuntimeException {

    public DuplicateProductNameException() {
        super("상품명이 이미 존재합니다.");
    }
}
