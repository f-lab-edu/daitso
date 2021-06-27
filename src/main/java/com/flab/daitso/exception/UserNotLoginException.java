package com.flab.daitso.exception;

public class UserNotLoginException extends RuntimeException {

    public UserNotLoginException() {
        super("로그인 상태가 아닙니다.");
    }
}
