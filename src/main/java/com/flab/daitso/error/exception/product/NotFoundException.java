package com.flab.daitso.error.exception.product;
// 상품을 찾지 못했을때를 나타내는 예외 클래스
public class NotFoundException extends IllegalAccessException{
    public NotFoundException(String message){
        super(message);
    }
}
