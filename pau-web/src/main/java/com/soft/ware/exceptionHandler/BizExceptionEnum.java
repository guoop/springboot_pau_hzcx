package com.soft.ware.exceptionHandler;

import com.soft.ware.core.exception.ServiceExceptionEnum;

public enum BizExceptionEnum implements ServiceExceptionEnum {



    ERROR(500,"未知错误");

    private int code;
    private String message;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    BizExceptionEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }
}
