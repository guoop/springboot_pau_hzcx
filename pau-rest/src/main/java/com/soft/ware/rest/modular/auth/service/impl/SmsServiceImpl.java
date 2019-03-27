package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.service.SmsService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class SmsServiceImpl implements SmsService {


    private Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public boolean sendNotify(String phone,String templateId,String businessId,String... values) {
        boolean matches = Pattern.matches("^\\d{11}([,，]\\d{11})*$", phone);
        if (!matches) {
            throw new PauException(BizExceptionEnum.SMS_ERROR_PHONE_FORMAT);
        }
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> phoneMap = new HashMap<>();
        String timestamp = DateUtil.timestampToDate();
        map.put("params", Arrays.asList(values));
        map.put("sign", "汇智创享");

        if (phone.contains(".")) {
            String[] ss = phone.split(",");
            List<Map<String,Object>> ps = new ArrayList<>();
            for (String s : ss) {
                phoneMap = new HashMap<>();
                phoneMap.put("mobile", s);
                phoneMap.put("nationcode","86");
                ps.add(phoneMap);
            }
            map.put("tel", ps);
        }else{
            phoneMap.put("mobile", phone);
            phoneMap.put("nationcode","86");
            map.put("tel",phoneMap);

        }
        map.put("time", timestamp);
        map.put("tpl_id", templateId);
        //todo yancc random 是否需要改变
        map.put("sig", ToolUtil.getSHA256StrJava("appkey=" + WXContants.TENCENTMSG_APPKEY + "&random=142536&time=" + timestamp + "&mobile=" + phone));
        ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.TENCENTMSG_GATAWAY, map, String.class);
        if (entity.getStatusCodeValue() == 200) {
            logger.info("{} {}短信发送成功{}",businessId, phone, templateId);
            return true;
        }else{
            logger.info("{} {}短信发送失败{}",businessId, phone, templateId);
            return false;
        }
    }
}
