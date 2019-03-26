package com.soft.ware.rest.modular.auth.validator.dto;

/**
 * 验证的凭据
 *
 * @author paulo
 * @date 2017-08-27 13:27
 */
public interface Credence {

    /**
     * 凭据名称
     */
    String getCredenceName();

    /**
     * 密码或者是其他的验证码之类的
     */
    String getCredenceCode();
    
    /**
     * 小程序端获取登录的手机号
     */
    String  getPhoneName();
    
    
}
