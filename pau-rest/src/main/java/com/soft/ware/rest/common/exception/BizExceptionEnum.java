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
    USER_DISABLED(700, "账户被禁用"),
    USER_DELETED(700, "账户不存在或被删除"),

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


    JPUSH_USER_ADD_FAIL(500, "极光im添加用户失败"),
    
    /**
     * 对象为null
     */
   ISNULL(601,"对象不能为空"),
    
   NO_USER(609,"非法用户"),

   NO_OPTION(500,"非法操作"),

   ORDER_CREATE_FAIL(500,"下单失败"),

   ORDER_PAY_FAIL(500,"订单付款失败"),

    ORDER_STATUS_NOT_SUPPORT(500, "当前订单状态不允许"),
    ORDER_CANEL_FAIL(500,"该订单不支持取消操作"),
    ORDER_CONFIRM_FAIL(500,"该订单不支持确认操作"),
    ORDER_DELIVER_FAIL(500,"该订单不支持配送操作"),
    ORDER_DONE_FAIL(500,"该订单不支持完成操作"),
    ORDER_REFUND_NOT_SUPPORT(500,"该订单不支持退款操作"),
    ORDER_REFUND_RUNNING(500,"退款中，请稍后"),
    ORDER_DIFF_REFUND_RUNNING(500,"退款中，请稍后"),
    ORDER_REFUND_FINISHED(500,"已退款，请勿重复"),
    ORDER_DIFF_REFUND_FINISHED(500,"差价已退款，请勿重复"),


    GOODS_REPEAT(500,"商品已存在"),

   PHONE_EXISTS(500,"手机号码已存在"),

    //短信发送错误
   SMS_ERROR_PHONE_FORMAT(400,"手机号格式错误"),

    SMS_ERROR_SEND(400,"短信发送失败"),

    IM_USER_DELETE(500,"没有当前用户"),

    ERROR(500,"未知错误");





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
