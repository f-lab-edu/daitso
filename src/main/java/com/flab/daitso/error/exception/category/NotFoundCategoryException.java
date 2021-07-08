package com.flab.daitso.error.exception.category;

public class NotFoundCategoryException extends RuntimeException {

    public NotFoundCategoryException() {
        super("해당 카테고리를 찾을 수 없습니다.");
    }
}
