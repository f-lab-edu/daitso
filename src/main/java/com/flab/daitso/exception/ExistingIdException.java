package com.flab.daitso.exception;

import org.springframework.dao.DuplicateKeyException;

public class ExistingIdException extends DuplicateKeyException {

    public ExistingIdException() {
        super("이미 존재하는 ID 입니다.");
    }
}
