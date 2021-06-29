package com.flab.daitso.error;

public class ErrorObject {

    private String message;
    private String exceptionType;

    public ErrorObject(Exception e){
        this.message = e.getLocalizedMessage();
        this.exceptionType = e.getClass().getSimpleName();
    }

    public String getMessage() {
        return message;
    }

    public String getExceptionType() {
        return exceptionType;
    }
}