package com.soft.ware.rest.modular.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwnerGroups;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroup;
import com.soft.ware.rest.modular.auth.controller.dto.ImUser;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.TblOwnerGroupsService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

@Service
public class ImServiceImpl implements ImService {

    private Logger logger = LoggerFactory.getLogger(ImServiceImpl.class);

    @Autowired
    private TblOwnerGroupsService ownerGroupsService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TblOwnerGroupsService groupsService;


    public ResponseEntity<String> get(String path) throws Exception {
        // 执行execute()，发送请求
        ResponseEntity<String> entity = restTemplate.execute(WXContants.JG_GATEWAY + path, HttpMethod.GET, getRequestCallback(), getResponseExtractor(), new HashMap<>());
        return entity;
    }

    private ResponseEntity<String> del(String path) throws Exception {
        // 执行execute()，发送请求
        ResponseEntity<String> entity = restTemplate.execute(WXContants.JG_GATEWAY + path, HttpMethod.DELETE, getRequestCallback(), getResponseExtractor(), new HashMap<>());
        return entity;
    }

    private RequestCallback getRequestCallback() throws Exception {
        HttpEntity request = new HttpEntity(getJpushHeaders());
        // 构造execute()执行所需要的参数。
        //RequestCallback requestCallback = restTemplate.httpEntityCallback(request, String.class);
        Method m = restTemplate.getClass().getDeclaredMethod("httpEntityCallback", Object.class, Type.class);
        m.setAccessible(true);
        RequestCallback requestCallback = (RequestCallback) m.invoke(restTemplate, request, String.class);
        return requestCallback;
    }

    private ResponseExtractor<ResponseEntity<String>> getResponseExtractor() throws ReflectiveOperationException {
        //ResponseExtractor<ResponseEntity<String>> responseExtractor = restTemplate.responseEntityExtractor(String.class);
        Method m = restTemplate.getClass().getDeclaredMethod("responseEntityExtractor", Type.class);
        m.setAccessible(true);
        ResponseExtractor<ResponseEntity<String>> responseExtractor = (ResponseExtractor)m.invoke(restTemplate, String.class);
        return responseExtractor;
    }



    private MultiValueMap<String,String> getJpushHeaders(){
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.put("Content-type", Lists.newArrayList("application/json"));
        headers.put("Authorization", Lists.newArrayList("Basic " + Base64.getEncoder().encodeToString((WXContants.JG_APPKEY + ":" + WXContants.JG_MASTER_SECRET).getBytes())));
        return headers;
    }

    @Override
    public void sendNewOrderNotify(SessionUser user, TblOrder order){
        Kv<String, Object> body = Kv.obj().set("code","new_order");
        sendNotify(user, body,"新订单到达");
    }


    /**
     * 获取极光im用户
     * @param username
     * @return
     * @throws Exception
     */
    public ImUser getUser(String username) throws Exception {
        ResponseEntity<String> entity = get("/v1/users/" + username);
        if (entity.getStatusCodeValue() == 200) {
            return JSON.parseObject(entity.getBody(), ImUser.class);
        }
        return null;
    }


    /**
     * 删除极光im用户
     * @param username
     * @return
     * @throws Exception
     */
    public ResponseEntity<String> delUser(String username) throws Exception {
        ResponseEntity<String> del = del( "/v1/users/" + username);
        return del;
    }


    @Override
    public void addOrUpdateUsers(SessionUser user, TblOwnerStaff... ss) throws Exception {
        for (TblOwnerStaff s : ss) {
            String username = user.getOwner() + "-0-" + s.getPhone();
            String password = "Hzcx-owner";
            ImUser u = getUser(username);
            ArrayList<Kv<String, String>> params = Lists.newArrayList(Kv.by("username", username).set("password", password).set("nickname", ToolUtil.isEmpty(s.getName()) ? s.getPhone() : s.getName()));
            HttpEntity<String> http = new HttpEntity<>(JSON.toJSONString(params), getJpushHeaders());
            if (u == null) {
                //注册用户
                ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.JG_GATEWAY + "/v1/users", http, String.class);
                if (entity.getStatusCodeValue() != 200) {
                    throw new PauException(BizExceptionEnum.JPUSH_USER_ADD_FAIL);
                }
            } else {
                //更新用户
                ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.JG_GATEWAY + "/v1/users/" + username, http, String.class);
                if (entity.getStatusCodeValue() != 200) {
                    throw new PauException(BizExceptionEnum.JPUSH_USER_ADD_FAIL);
                }
            }
            addGroup(user, s);
        }
    }


    public Kv<String,Object> buildBaseMsg(TblOwnerGroups groups,ImGroup g,Kv<String,Object> body){
        Kv<String, Object> params = Kv.init();
        params.put("version", 1);
        params.put("target_type", "group");
        params.put("target_id",groups.getGid());
        params.put("from_type", "admin");
        params.put("from_id", g.getOwner_username());
        params.put("msg_type", "custom");
        params.put("msg_body", body);
        return params;
    }

    private void sendNotify(SessionOwnerUser user,Kv<String,Object> body,String log){
        List<TblOwnerGroups> groups = ownerGroupsService.find(user, TblOwnerGroups.type_0);
        for (TblOwnerGroups group : groups) {
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
            Kv<String, Object> params = buildBaseMsg(group, g, body);
            HttpEntity<String> http = new HttpEntity<>(JSON.toJSONString(params), getJpushHeaders());
            ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.JG_GATEWAY + "/v1/messages", http, String.class);
            if (entity.getStatusCodeValue() == 200) {
                logger.info(log + " 通知发送成功: "+JSON.toJSONString(params));
            } else {
                logger.info(log + " 通知发送失败：" + JSON.toJSONString(params));
            }
        }

    }


    @Override
    public void sendAddGoodsNotify(SessionOwnerUser user, TblGoods goods) {
        Kv<String, Object> body = Kv.obj().set("code","add_goods").set("handlerBy",user.getName()).set("goods", goods);
        sendNotify(user, body,"商品添加");
    }


    public void addGroup(SessionUser user, TblOwnerStaff s) {
        List<TblOwnerGroups> groups = groupsService.find(user, TblOwnerGroups.type_0);
        for (TblOwnerGroups group : groups) {
            String username = user.getOwner() + "-0-" + s.getPhone();
            Kv<String, String> params = Kv.by("username", username);
            HttpEntity<String> http = new HttpEntity<>(JSON.toJSONString(params), getJpushHeaders());
            ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.JG_GATEWAY + "/v1/groups/" + group.getGid() + "/members", http, String.class);
            if (entity.getStatusCodeValue() != 200) {
                throw new PauException(BizExceptionEnum.JPUSH_USER_ADD_FAIL);
            }
        }
    }

}
