package com.soft.ware.rest.modular.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.persistence.model.*;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroupType;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.ImGroupsService;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.ImUserService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.im_groups.model.SImGroups;
import com.soft.ware.rest.modular.im_groups.service.ISImGroupsService;
import com.soft.ware.rest.modular.order.model.TOrder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * 极光im群组/用户管理类
 * //todo yancc 没有关于群组超出500人后的对策
 * //todo yancc 密码使用固定密码，不安全
 * //todo yancc 尚未处理收银机用户（android/windows）
 */
@Service
@Transactional
public class ImServiceImpl implements ImService {

    private Logger logger = LoggerFactory.getLogger(ImServiceImpl.class);

    @Autowired
    private ISImGroupsService imGroupsService;

    @Autowired
    private RestTemplate restTemplate;

    private String password = "Hzcx-owner";

    @Autowired
    private ImUserService imUserService;

    @Autowired
    private ImGroupsService groupsService;



    /**
     * 发送新订单通知
     * @param user
     * @param order
     */
    @Deprecated
    @Override
    public void sendNewOrderNotify(SessionUser user, TOrder order){
        Kv<String, Object> body = Kv.obj().set("code","new_order");
        sendNotify(user, body,"新订单到达");
    }


    /**
     * 发送添加商品通知
     * @param user
     * @param goods
     */
    @Override
    public void sendAddGoodsNotify(SessionUser user, TGoods goods) {
        Kv<String, Object> body = Kv.obj().set("code", "add_goods").set("handlerBy", user.getName()).set("goods", goods);
        sendNotify(user, body,"商品添加");
    }




    /**
     * 添加或删除用户
     * @param user
     * @param ss
     * @throws Exception
     */
    @Override
    public void syncUsers(SessionUser user,TblOwner owner, TblOwnerStaff... ss) throws Exception {
        ImGroupType type = ImGroupType.STAFF;//极光小程序用户
        List<Kv<String, ?>> params = Lists.newArrayList();
        String username;
        for (TblOwnerStaff s : ss) {
            username = buildUsername(s, type);
            ImUser u = getUser(username);
            if (TblOwnerStaff.status_0.equals(s.getStatus())) {
                //用户状态正常
                if (u == null) {
                    //注册用户
                    params.add(Kv.by("username", username).set("password", password).set("nickname", ToolUtil.isEmpty(s.getName()) ? s.getPhone() : s.getName()));
                    u = addUser(user, params);
                    imUserService.saveOrUpdate(user,u);
                    ImGroups g = requireOwnerGroup(user, owner, type);
                    if (!hasUser(g, u)) {
                        //添加到群组
                        addToGroup(user, g, u);
                        groupsService.saveOrUpdate(user,g);
                    }
                    logger.info("极光用户:{}被添加",username);
                } else {
                    //更新用户
                    Kv<String,?> kv = Kv.by("username", username).set("password", password).set("nickname", ToolUtil.isEmpty(s.getName()) ? s.getPhone() : s.getName());
                    u.setOwnerId(user.getOwnerId());
                    updateUser(user,username, kv);
                    u = getUser(username);
                    imUserService.saveOrUpdate(user,u);
                    logger.info("极光用户:{}被更新",username);
                    u = getUser(username);
                    ImGroups g = requireOwnerGroup(user, owner, type);
                    if (!hasUser(g, u)) {
                        //添加到群组
                        addToGroup(user, g, u);
                        groupsService.saveOrUpdate(user,g);
                    }
                }

            } else {
                //状态异常删除用户
                username = buildUsername(s, type);
                if (u != null) {
                    ImGroups g = requireOwnerGroup(user, owner, type);
                    if (g != null) {
                        //delFromGroup(user,g,u);//经过测试，删除用户时极光会自动删除他所在的群组
                        groupsService.deleteByUsername(user, g.getOwnerUsername());
                    }
                    delUser(username);
                    imUserService.deleteByUsername(user,username);
                }
                logger.info("极光用户:{}被删除",username);
            }
        }
    }


    /**
     * 获取群主信息
     * @param owner
     * @return
     */
    private ImGroups requireOwnerGroup(SessionUser user,TblOwner owner,ImGroupType type){
        String username = buildOwnerGroupUsername(owner,type);
        ImUser u = getUser(username);
        if (u == null) {
            buildOwnerGroupUsername(owner, type);
            List<Kv<String, ?>> params = Lists.newArrayList();
            params.add(Kv.by("username", username).set("password", password).set("nickname", ToolUtil.isEmpty(owner.getName()) ? owner.getPhone() : owner.getName()));
            //创建群主
            u = addUser(user, params);
            //创建群组
            return addGroup(user, owner, type);
        } else {
            List<ImGroups> gs = getGroup(u);
            if (gs.isEmpty()) {
                //创建群组
                return addGroup(user, owner, type);
            }
            //todo yancc 是否考虑多个群
            return gs.get(0);
        }
    }


    /**
     * 发送get请求
     * @param path
     * @return
     * @throws Exception
     */
    private ResponseEntity<String> get(String path) throws Exception {
        ResponseEntity<String> entity = restTemplate.execute(WXContants.JG_GATEWAY + path, HttpMethod.GET, getRequestCallback(), getResponseExtractor(), new HashMap<>());
        if (entity.getStatusCodeValue() != 200) {
            throw new RestClientException("get请求失败");
        }
        return entity;
    }

    /**
     * 发送del请求
     * @param path
     * @return
     * @throws Exception
     */
    private ResponseEntity<String> del(String path) throws Exception {
        ResponseEntity<String> entity = restTemplate.execute(WXContants.JG_GATEWAY + path, HttpMethod.DELETE, getRequestCallback(), getResponseExtractor(), new HashMap<>());
        if (entity.getStatusCodeValue() != 204) {
            throw new RestClientException("del请求失败");
        }
        return entity;
    }

    /**
     * 发送post请求
     * @param path
     * @param httpEntity
     * @return
     */
    private ResponseEntity<String> post(String path, HttpEntity<String> httpEntity){
        ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.JG_GATEWAY + path, httpEntity, String.class);
        if (entity.getStatusCodeValue() != 200 && entity.getStatusCodeValue() != 201 && entity.getStatusCodeValue() != 204) {
            throw new RestClientException("post请求失败");
        }
        return entity;
    }

    private ResponseEntity<String> post(String path, List<Kv<String,?>> params){
        return post(path, new HttpEntity<>(JSON.toJSONString(params), getJpushHeaders()));
    }

    private ResponseEntity<String> post(String path,Kv<String,?> params){
        return post(path, new HttpEntity<>(JSON.toJSONString(params), getJpushHeaders()));
    }

    /**
     * 发送put请求
     * @param path
     * @param params
     * @return
     */
    private void put(String path, Kv<String,?> params)  {
        HttpEntity<Object> http = new HttpEntity<>(params, getJpushHeaders());
        restTemplate.put(WXContants.JG_GATEWAY + path, http);
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
        headers.put("Content-type", Lists.newArrayList("application/json; charset=utf-8"));
        headers.put("Authorization", Lists.newArrayList("Basic " + Base64.getEncoder().encodeToString((WXContants.JG_APPKEY + ":" + WXContants.JG_MASTER_SECRET).getBytes())));
        return headers;
    }





    /**
     * 创建一个等待发送的基本消息
     * @param groups
     * @param g
     * @param body
     * @return
     */
    private Kv<String,Object> buildBaseMsg(SImGroups groups,ImGroups g,Kv<String,Object> body){
        Kv<String, Object> params = Kv.init();
        params.put("version", 1);
        params.put("target_type", "group");
        params.put("target_id",groups.getGid());
        params.put("from_type", "admin");
        params.put("from_id", g.getOwnerUsername());
        params.put("msg_type", "custom");
        params.put("msg_body", body);
        return params;
    }

    /**
     * 发送通知
     * @param user 发送者
     * @param body 消息体
     * @param log  日志前缀
     */
    private void sendNotify(SessionUser user,Kv<String,Object> body,String log){
        List<SImGroups> groups = imGroupsService.find(user, TblOwnerGroups.type_0);
        for (SImGroups group : groups) {
            ImGroups g = JSON.parseObject(group.getBody(), ImGroups.class);
            if (g == null) {
                continue;
            }
            if (StringUtils.isBlank(g.getOwnerUsername())) {
                continue;
            }
            if (g.getGid() == null) {
                continue;
            }
            Kv<String, Object> params = buildBaseMsg(group, g, body);
            HttpEntity<String> http = new HttpEntity<>(JSON.toJSONString(params), getJpushHeaders());
            ResponseEntity<String> entity = restTemplate.postForEntity(WXContants.JG_GATEWAY + "/v1/messages", http, String.class);
            if (entity.getStatusCodeValue() == 200 || entity.getStatusCodeValue() == 201) {
                logger.info(log + " 通知发送成功: "+JSON.toJSONString(params));
            } else {
                logger.info(log + " 通知发送失败：" + JSON.toJSONString(params));
            }
        }

    }


    /**
     * 统一用户名命名规则
     * @param u
     * @param type
     * @return
     */
    private String buildUsername(TblOwnerStaff u,ImGroupType type){
        return u.getOwner() + "-" + type.getSeparator() + "-" + u.getPhone();
    }

    /**
     * 统一群组管理员命名规则
     * @param owner
     * @param type
     * @return
     */
    private String buildOwnerGroupUsername(TblOwner owner, ImGroupType type){
        return owner.getOwner() + "--" + type.name();
    }


    /**
     * 添加极光用户
     * @param user
     * @param params
     * @return
     */
    private ImUser addUser(SessionUser user,List<Kv<String,?>> params) {
        ResponseEntity<String> entity = post("/v1/users", params);
        List<ImUser> users = JSON.parseArray(entity.getBody(), ImUser.class);
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    /**
     * 修改用户信息
     * @param user
     * @param params
     * @throws Exception
     */
    private void updateUser(SessionUser user,String username,Kv<String,?> params) throws Exception {
        //todo yancc 是否需要重置密码
        put("/v1/users/" + username, params);
    }

    /**
     * 获取极光im用户
     * @param username
     * @return
     * @throws Exception
     */
    private ImUser getUser(String username) {
        try {
            ResponseEntity<String> entity = get("/v1/users/" + username);
            return JSON.parseObject(entity.getBody(), ImUser.class);
        } catch (HttpClientErrorException e) {
            logger.warn("极光用户：{} 不存在",username);
            //e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除极光用户
     * @param username
     * @throws Exception
     */
    private void delUser(String username) throws Exception {
        del("/v1/users/" + username);
    }

    /**
     * 创建群组
     * @param user
     * @param owner
     * @param type 群组类型
     * @return
     */
    private ImGroups addGroup(SessionUser user, TblOwner owner,ImGroupType type){
        String username = buildOwnerGroupUsername(owner,type);
        Kv<String, Object> params = Kv.obj().set("owner_username", username).set("name", owner.getName()).set("desc", type.getDesc()).set("members_username", new String[]{});
        ResponseEntity<String> entity = post("/v1/groups/", params);
        ImGroups imGroups = JSON.parseObject(entity.getBody(), ImGroups.class);
        if (imGroups != null) {
            imGroups.setOwnerUsername(username);
        }
        return imGroups;
    }

    /**
     * 获取群组信息
     * @param gid
     * @return
     * @throws Exception
     */
    private ImGroups getGroup(String gid) throws Exception {
        ResponseEntity<String> entity = get("/v1/groups/" + gid);
        return JSON.parseObject(entity.getBody(), ImGroups.class);
    }


    /**
     * 获取用户的群组
     * @param user
     * @return
     * @throws Exception
     */
    private List<ImGroups> getGroup(ImUser user)  {
        try {
            ResponseEntity<String> entity = get("/v1/users/" + user.getUsername() + "/groups/");
            List<ImGroups> imGroups = JSON.parseArray(entity.getBody(), ImGroups.class);
            for (ImGroups g : imGroups) {
                g.setOwnerUsername(user.getUsername());
            }
            return imGroups;
        } catch (Exception e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }



    /**
     * 更新群组信息
     * @param user
     * @param owner
     * @throws Exception
     */
    private void updateGroup(SessionUser user,TblOwner owner,ImGroupType type) throws Exception {
        Kv<String, String> params = Kv.by("owner_username", buildOwnerGroupUsername(owner, type)).set("name", owner.getName());
        put("/v1/groups", params);
    }


    /**
     * 删除群组信息
     * @param user
     * @param owner
     * @throws Exception
     */
    private void delGroup(SessionUser user,TblOwner owner,ImGroupType type) throws Exception {
        del("/v1/groups/" + buildOwnerGroupUsername(owner, type));
    }


    /**
     * 添加用户到群组
     * @param user
     * @param group
     * @param u
     */
    public void addToGroup(SessionUser user,ImGroups group, ImUser u) {
        Kv<String,?> params = Kv.by("add",Lists.newArrayList(u.getUsername()));
        post("/v1/groups/" + group.getGid() + "/members", params);
    }


    /**
     * 删除用户从群组
     * @param user
     * @param group
     * @param u
     */
    public void delFromGroup(SessionUser user,ImGroups group, ImUser u) {
        Kv<String,?> params = Kv.by("remove",Lists.newArrayList(u.getUsername()));
        post( "/v1/groups/" + group.getGid() + "/members", params);
    }

    /**
     * 返回群组所有用户
     * @param group
     * @return
     */
    public List<ImUser> getUsers(ImGroups group){
        try {
            ResponseEntity<String> entity = get("/v1/groups/" + group.getGid() + "/members");
            return JSON.parseArray(entity.getBody(), ImUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    /**
     * 查询这个群组是否包含这个用户
     * @param group
     * @param u
     * @return
     */
    public boolean hasUser(ImGroups group,ImUser u){
        List<ImUser> users = getUsers(group);
        return users.stream().anyMatch(s -> s.getUsername().equals(u.getUsername()));
    }



}
