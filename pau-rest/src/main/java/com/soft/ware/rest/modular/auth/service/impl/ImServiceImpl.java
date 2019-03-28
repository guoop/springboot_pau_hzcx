package com.soft.ware.rest.modular.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwnerGroups;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroup;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.TblOwnerGroupsService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImServiceImpl implements ImService {

    private Logger logger = LoggerFactory.getLogger(ImServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TblOwnerGroupsService ownerGroupsService;

    @Autowired
    private RestTemplate restTemplate;

    private MultiValueMap<String,String> getJpushHeaders(){
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.put("Content-type", Lists.newArrayList("application/json"));
        headers.put("Authorization", Lists.newArrayList("Basic " + Base64.getEncoder().encodeToString((WXContants.JG_APPKEY + ":" + WXContants.JG_MASTER_SECRET).getBytes())));
        return headers;
    }

    @Override
    public void sendNewOrderNotify(SessionUser user, TblOrder order){
        List<TblOwnerGroups> groups = ownerGroupsService.find(user, TblOwnerGroups.type_0);
        for (TblOwnerGroups group : groups) {
            if (StringUtils.isBlank(group.getBody())) {
                continue;
            }
            ImGroup g = JSON.parseObject(group.getBody(), ImGroup.class);
            if (g == null) {
                continue;
            }
            if (StringUtils.isBlank(g.getOwner_username())) {
                continue;
            }
            if (g.getGid() == null) {
                continue;
            }
            Map<String,Object> params = new HashMap<>();
            params.put("version", 1);
            params.put("target_type", "group");
            params.put("target_id",group.getGid());
            params.put("from_type", "admin");
            params.put("from_id", g.getOwner_username());
            params.put("msg_type", "custom");
            Map<String, Object> body = new HashMap<>();
            body.put("code", "new_order");
            params.put("msg_body", body);
            HttpEntity<String> http = new HttpEntity<>(JSON.toJSONString(params), getJpushHeaders());
            ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.JG_GATEWAY + "/v1/messages", http, String.class);
            logger.info(order.getNo() + "新订单到达时群发消息返回状态" + entity.getStatusCodeValue());
            logger.info(order.getNo() + "新订单到达时群发消息返回结果：" + JSON.toJSONString(entity.getBody()));
        }

    }

}
