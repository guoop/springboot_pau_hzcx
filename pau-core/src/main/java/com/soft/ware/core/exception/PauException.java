package com.soft.ware.core.exception;

/**
 * 封装pau的异常
 */
public class PauException extends RuntimeException {

    private Integer code;

    private String message;

    public PauException(String message) {
        this.code = 500;
        this.message = message;
    }

    public PauException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
