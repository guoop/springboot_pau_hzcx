package com.soft.ware.rest.modular.auth.service;

public interface SmsService {



    /**
     * 发送发送通知短信
     * @param phone      目标手机号 支持 使用逗号分(,)隔发送到多个手机号
     * @param templateId 短信模板id
     * @param templateId 业务Id,例如订单号 方便记录日志
     * @see com.soft.ware.rest.modular.auth.util.WXContants#TENCENT_TEMPLATE_ID1
     * @see com.soft.ware.rest.modular.auth.util.WXContants#TENCENT_TEMPLATE_ID2
     * @see com.soft.ware.rest.modular.auth.util.WXContants#TENCENT_TEMPLATE_ID3
     * @param values     参数列表
     * @return
     */
    boolean sendNotify(String phone, String templateId,String businessId, String... values);
}
