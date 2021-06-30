package com.flab.daitso.error.exception.product;
//  품절 상태를 나타내기 위한 에외 클래스
public class SoldOutException extends IllegalAccessException{
    public SoldOutException(String message){
        super(message);
    }
}
