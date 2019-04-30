package com.soft.ware.core.exception;


public class SessionException extends RuntimeException {

    public SessionException() {
        super("会话过期，请重新登录");
    }
}
