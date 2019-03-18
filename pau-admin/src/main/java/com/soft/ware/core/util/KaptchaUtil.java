package com.soft.ware.core.util;

import com.soft.ware.config.properties.PauProperties;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(PauProperties.class).getKaptchaOpen();
    }
}