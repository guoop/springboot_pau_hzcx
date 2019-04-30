package com.soft.ware.core.exception;

public class ParameterException extends RuntimeException implements ServiceExceptionEnum {


    public ParameterException(String msg) {
        super(msg);
    }

    @Override
    public Integer getCode() {
        return 400;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
