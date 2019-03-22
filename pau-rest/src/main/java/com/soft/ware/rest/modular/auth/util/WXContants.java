package com.soft.ware.rest.modular.auth.util;

public class WXContants {
	
	//微信小程序网关
	public static final String WX_GETWAY = "https://api.weixin.qq.com";

	//微信小程序登录接口
	public static final String WX_LOGIN = WX_GETWAY+"/sns/jscode2session";
	
	//用户信息 
	public static final String WX_USER_INFO = WX_GETWAY+"/wxa/getpaidunionid";
	
	//调用凭证
	public static final String WX_TOKEN = WX_GETWAY+"/cgi-bin/token";
	
	//腾讯云短信服务
	public static final String TENCENTMSG_GATAWAY = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";
	//腾讯云appid
	public static final String TENCENTMSG_APPID = "1400165586";
    //腾讯云app key
	public static final String TENCENTMSG_APPKEY = "de11c98a0841ddb851e7b718d221cd0d";
	
	
}
