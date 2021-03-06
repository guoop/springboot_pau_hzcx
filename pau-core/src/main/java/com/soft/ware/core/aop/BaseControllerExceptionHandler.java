package com.soft.ware.core.aop;

import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.exception.PauExceptionEnum;
import com.soft.ware.core.exception.SessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 */
public class BaseControllerExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 拦截业务异常
     */
    @ExceptionHandler(SessionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorTip notFount(SessionException e) {
        log.error("业务异常:", e);
        //todo yancc 是否让前端改，httpClient.js
        return new ErrorTip(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    /**
     * 拦截业务异常
     */
    @ExceptionHandler(PauException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ErrorTip notFount(PauException e) {
        log.error("业务异常:", e);
        //todo yancc 是否让前端改，httpClient.js
        if (e.getCode() != 0) {
            return new ErrorTip("fail", e.getMessage());
        }
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return new ErrorTip(PauExceptionEnum.SERVER_ERROR.getCode(), PauExceptionEnum.SERVER_ERROR.getMessage());
    }

}
