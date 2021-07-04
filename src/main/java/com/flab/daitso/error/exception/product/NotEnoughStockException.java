현package com.flab.daitso.error.exception.product;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super("재고가 없습니다.");
    }
}
