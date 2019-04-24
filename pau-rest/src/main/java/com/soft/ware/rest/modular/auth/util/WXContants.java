package com.soft.ware.rest.modular.auth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class WXContants {


	@Value(value = "${wx.pay.notify_host}")
	private String customerPayHost;

	@Value(value = "${wx.pay.notify_url_customer_pay_pickup}")
	private String customerPayPickup;

	@Value(value = "${wx.pay.notify_url_customer_pay}")
	private String customerPay;

	@Value(value = "${wx.pay.notify_url_customer_pay_diff}")
	private String customerPayDiff;

	//用户登录,验证码redis key 前缀
	public final static String loginCodePrefix = "owner:sms_login_code";
	//用户登录,验证码时常 秒
	public final static Long loginCodeExpire = 120L;

	//商家小程序id
	public final static String OWNER_APP_ID = "wxde3d576b9b28b080";
	public final static String OWNER_APP_SECRET = "f6c2462960af2139fe0eea1a6ae28647";

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
	/**
	 * 群发接口
	 */
	public static final String TENCENTMSG_GATAWAY2 = "https://yun.tim.qq.com/v5/tlssmssvr/sendmultisms2?sdkappid=1400165586&random=142536";
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
	//订单通知短信模板
	public static final String TENCENT_TEMPLATE_ID4 = "241324";

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

	//默认日期时间格式
	public static final String date_format = "YYYY-MM-DD HH:mm:ss";
	//默认金额显示，格式
	public static final int big_decimal_sale = BigDecimal.ROUND_HALF_UP;


	public String getCustomerPayHost() {
		return customerPayHost;
	}

	public void setCustomerPayHost(String customerPayHost) {
		this.customerPayHost = customerPayHost;
	}

	public String getCustomerPayPickup() {
		return customerPayPickup;
	}

	public void setCustomerPayPickup(String customerPayPickup) {
		this.customerPayPickup = customerPayPickup;
	}

	public String getCustomerPay() {
		return customerPay;
	}

	public void setCustomerPay(String customerPay) {
		this.customerPay = customerPay;
	}

	public String getCustomerPayDiff() {
		return customerPayDiff;
	}

	public void setCustomerPayDiff(String customerPayDiff) {
		this.customerPayDiff = customerPayDiff;
	}
}
