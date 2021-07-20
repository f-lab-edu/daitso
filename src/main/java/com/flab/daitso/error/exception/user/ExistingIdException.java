package com.flab.daitso.error.exception.user;

public class ExistingIdException extends RuntimeException {

    public ExistingIdException() {
        super("이미 존재하는 ID 입니다.");
    }
}
