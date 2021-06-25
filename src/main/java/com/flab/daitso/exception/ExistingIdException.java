package com.flab.daitso.exception;

public class ExistingIdException extends RuntimeException {

    public ExistingIdException() {
        super("이미 존재하는 ID 입니다.");
    }
}
