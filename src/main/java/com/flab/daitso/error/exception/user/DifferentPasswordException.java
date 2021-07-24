package com.flab.daitso.error.exception.user;

public class DifferentPasswordException extends RuntimeException {

    public DifferentPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
