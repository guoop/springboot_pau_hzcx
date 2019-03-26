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
	
	/**
	 * 腾讯云短信服务
	 */
	public static final String TENCENTMSG_GATAWAY = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms?sdkappid=1400165586&random=142536";
	//appid
	public static final String TENCENTMSG_APPID = "1400165586";
    //app key
	public static final String TENCENTMSG_APPKEY = "de11c98a0841ddb851e7b718d221cd0d";
	//短信模板3验证码
	public static final String TENCENT_TEMPLATE_ID3 = "241314";
	//短信模板2订单通知
	public static final String TENCENT_TEMPLATE_ID2 = "241320";
	//短信模板1自主版订单通知
	public static final String TENCENT_TEMPLATE_ID1 = "241314";
	
	/**
	 * 极光推送
	 */
	//应用名称
	public static final String JG_APPLICATION_NAME = "cashier-counter";
	//应用appkey
	public static final String JG_APPKEY = "4650a50ded60a4bd9f52518a";
	//应用master_secret
	public static final String JG_MASTER_SECRET = "0da6b113a07bd3454d798bdb";
	//网关
	public static final String JG_GATEWAY = "https://api.im.jpush.cn";
	
	
	
	
}
