package com.soft.ware.rest.common.exception;

import com.soft.ware.core.exception.ServiceExceptionEnum;


/**
 * 所有业务异常的枚举
 *
 * @author paulo
 * @date 2016年11月12日 下午5:04:51
 */
public enum BizExceptionEnum implements ServiceExceptionEnum {

    /**
     * token异常
     */
    TOKEN_EXPIRED(700, "token过期"),
    TOKEN_ERROR(700, "token验证失败"),
    
    PARAME_ERROR(400,"参数错误"),

    /**
     * 签名异常
     */
    SIGN_ERROR(700, "签名验证失败"),

    /**
     * 修改失败
     */
    UPDATE_ERROR(500,"修改失败"),

    /**
     * 添加失败
     */
    ADD_ERROR(500,"添加失败"),

    /**
     * 其他
     */
    AUTH_REQUEST_ERROR(400, "账号密码错误"),
    
    /**
     * 对象为null
     */
   ISNULL(601,"对象为空");


    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
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
