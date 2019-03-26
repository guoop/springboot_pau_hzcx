package com.soft.ware.rest.modular.auth.util;

import javax.servlet.http.HttpServletRequest;

public class WxUtils {


    public static String getAppId(HttpServletRequest request){
        String referer = request.getHeader("Referer");
        String[] split = referer.split("/");
        String domain = split[2];
        String appId = split[3];
        return appId;
    }

}
