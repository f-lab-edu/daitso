package com.flab.daitso.error.exception;

public class NotExistingIdException extends RuntimeException {

    public NotExistingIdException() {
        super("존재하지 않는 ID 입니다.");
    }
}
