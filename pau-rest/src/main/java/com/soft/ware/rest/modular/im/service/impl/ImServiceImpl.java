package com.soft.ware.rest.modular.im.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroupType;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.im.model.SImGroups;
import com.soft.ware.rest.modular.im.model.SImUser;
import com.soft.ware.rest.modular.im.service.ISImGroupsService;
import com.soft.ware.rest.modular.im.service.ISImUserService;
import com.soft.ware.rest.modular.im.service.ImService;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
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
    private ISImUserService imUserService;

    @Autowired
    private ITOwnerService ownerService;



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
     * 创建一个等待发送的基本消息
     * @param group
     * @param body
     * @return
     */
    private Kv<String,Object> buildBaseMsg(SImGroups group,Kv<String,Object> body){
        Kv<String, Object> params = Kv.init();
        params.put("version", 1);
        params.put("target_type", "group");
        params.put("target_id",group.getGid());
        params.put("from_type", "admin");
        params.put("from_id", group.getOwnerUsername());
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
        List<SImGroups> groups = requireOwnerGroupList(user, null, ImGroupType.STAFF);
        for (SImGroups group : groups) {
            if (StringUtils.isBlank(group.getOwnerUsername())) {
                continue;
            }
            if (group.getGid() == null) {
                continue;
            }
            Kv<String, Object> params = buildBaseMsg(group, body);
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
     * 添加或删除用户
     * @param user
     * @param ss
     * @throws Exception
     */
    @Override
    public void syncUsers(SessionUser user, TOwner owner, TOwnerStaff... ss) throws Exception {
        ImGroupType type = ImGroupType.STAFF;//极光小程序用户
        String username;
        for (TOwnerStaff s : ss) {
            username = ParamUtils.buildImUserName(s, type);
            SImUser u = getUser(username);
            if (TOwnerStaff.status_0.equals(s.getStatus())) {
                //用户状态正常
                if (u == null) {
                    //注册用户
                    u = new SImUser().setUsername(username).setPassword(password).setNickname(ToolUtil.isEmpty(owner.getName()) ? owner.getPhone() : owner.getName());
                    addUser(user, u);
                    imUserService.saveOrUpdate(user,u);
                    SImGroups g = requireOwnerGroup(user, owner, type);
                    if (!hasUser(g, u)) {
                        //添加到群组
                        addToGroup(user, g, u);
                    }
                    logger.info("极光用户:{}被添加",username);
                } else {
                    //更新用户
                    u.setOwnerId(user.getOwnerId());
                    u.setNickname(ToolUtil.isEmpty(user.getName()) ? s.getPhone() : s.getName());
                    u.setUsername(username);
                    u.setPassword(password);
                    updateUser(user,username,u);
                    imUserService.saveOrUpdate(user,u);
                    logger.info("极光用户:{}被更新",username);
                    SImGroups g = requireOwnerGroup(user, owner, type);
                    if (!hasUser(g, u)) {
                        //添加到群组
                        addToGroup(user, g, u);
                    }
                }

            } else {
                //状态异常删除用户
                username = ParamUtils.buildImUserName(s, type);
                if (u != null) {
                    SImGroups g = requireOwnerGroup(user, owner, type);
                    if (g != null) {
                        //delFromGroup(user,g,u);//经过测试，删除用户时极光会自动删除他所在的群组
                        imGroupsService.deleteByUsername(user, g.getOwnerUsername());
                    }
                    delUser(username);
                    imUserService.deleteByUsername(user,username);
                }
                logger.info("极光用户:{}被删除",username);
            }
        }
    }


    private SImGroups requireOwnerGroup(SessionUser user,TOwner owner,ImGroupType type){
        if (owner == null) {
            owner = ownerService.find(user);
        }
        //先查数据库
        List<SImGroups> gs = imGroupsService.find(user, type.ordinal());
        for (SImGroups g : gs) {
            if (g.getOwnerUsername().equals(ParamUtils.buildOwnerGroupUsername(owner, type))) {
                return g;
            }
        }
        //再差远程
        gs = requireOwnerGroupList(user, owner, type);
        for (SImGroups g : gs) {
            if (g.getOwnerUsername().equals(ParamUtils.buildOwnerGroupUsername(owner, type))) {
                return g;
            }
        }
        return gs.get(0);
    }

    /**
     * 获取群主信息
     * @param owner
     * @return
     */
    private List<SImGroups> requireOwnerGroupList(SessionUser user,TOwner owner,ImGroupType type){
        //todo yancc 对于群组和用户每次获取远程并检查数据库，没有必要
        if (owner == null) {
            owner = ownerService.find(user);
        }
        String username = ParamUtils.buildOwnerGroupUsername(owner,type);
        SImUser u = getUser(username);
        if (u == null) {
            ParamUtils.buildOwnerGroupUsername(owner,type);
            //创建群主
            u = new SImUser().setOwnerId(user.getOwnerId()).setUsername(username).setPassword(password).setNickname(ToolUtil.isEmpty(owner.getName()) ? owner.getPhone() : owner.getName());
            addAdmin(u);
            imUserService.insert(u);
            //创建群组
            SImGroups group = addGroup(user, owner, type, u);
            group.setType(type.ordinal());
            imGroupsService.saveOrUpdate(user, group);
            return getGroup(u);
        } else {
            List<SImGroups> gs = getGroup(u);
            if (!gs.isEmpty()) {
                imUserService.saveOrUpdate(user, u);
                //创建群组
                SImGroups group = addGroup(user, owner, type, u);
                group.setType(type.ordinal());
                imGroupsService.saveOrUpdate(user, group);
                return getGroup(u);
            }
            //todo yancc 是否考虑多个群
            return gs;
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

    private ResponseEntity<String> post(String path, List<Kv<String,Object>> params){
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
     * 统一用户名命名规则
     * @param u
     * @param type
     * @return
     */
    private String buildUsername(TOwnerStaff u,ImGroupType type){
        return u.getOwnerId() + "-" + type.getSeparator() + "-" + u.getPhone();
    }

    /**
     * 统一群组管理员命名规则
     * @param owner
     * @param type
     * @return
     */
    private String buildOwnerGroupUsername(TOwner owner, ImGroupType type){
        return owner.getId() + "--" + type.name();
    }


    /**
     * 添加极光用户
     * @param user
     * @param u   极光用户
     * @return
     */
    private SImUser addUser(SessionUser user,SImUser u) {
        Kv<String, Object> params = Kv.obj("username", u.getUsername()).set("password", u.getPassword()).set("nickname", u.getNickname());
        List<Kv<String,Object>> us = Lists.newArrayList();
        us.add(params);
        ResponseEntity<String> entity = post("/v1/users",us);
        List<SImUser> users = JSON.parseArray(entity.getBody(), SImUser.class);
        return users.get(0);
    }

    /**
     * 修改用户信息
     * @param user
     * @param u
     * @throws Exception
     */
    private void updateUser(SessionUser user,String username,SImUser u) throws Exception {
        //todo yancc 是否需要重置密码
        Kv<String, ?> kv = Kv.by("username", username).set("password", password).set("nickname", u.getNickname());
        put("/v1/users/" + username, kv);
    }

    /**
     * 获取极光im用户
     * @param username
     * @return
     * @throws Exception
     */
    public SImUser getUser(String username) {
        try {
            ResponseEntity<String> entity = get("/v1/users/" + username);
            return JSON.parseObject(entity.getBody(), SImUser.class);
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
    private SImGroups addGroup(SessionUser user, TOwner owner,ImGroupType type,SImUser admin){
        Kv<String, Object> params = Kv.obj().set("owner_username", admin.getUsername()).set("name", owner.getName()).set("desc", type.getDesc()).set("members_username", new String[]{});
        ResponseEntity<String> entity = post("/v1/groups/", params);
        return JSON.parseObject(entity.getBody(), SImGroups.class);
    }

    /**
     * 获取群组信息
     * @param gid
     * @return
     * @throws Exception
     */
    private SImGroups getGroup(String gid) throws Exception {
        ResponseEntity<String> entity = get("/v1/groups/" + gid);
        return JSON.parseObject(entity.getBody(), SImGroups.class);
    }


    /**
     * 获取用户的群组
     * @param user
     * @return
     * @throws Exception
     */
    private List<SImGroups> getGroup(SImUser user)  {
        try {
            ResponseEntity<String> entity = get("/v1/users/" + user.getUsername() + "/groups/");
            List<SImGroups> imGroups = JSON.parseArray(entity.getBody(), SImGroups.class);
            for (SImGroups g : imGroups) {
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
    private void updateGroup(SessionUser user,TOwner owner,ImGroupType type) throws Exception {
        Kv<String, String> params = Kv.by("owner_username", buildOwnerGroupUsername(owner, type)).set("name", owner.getName());
        put("/v1/groups", params);
    }


    /**
     * 删除群组信息
     * @param user
     * @param owner
     * @throws Exception
     */
    private void delGroup(SessionUser user,TOwner owner,ImGroupType type) throws Exception {
        del("/v1/groups/" + buildOwnerGroupUsername(owner, type));
    }


    /**
     * 添加用户到群组
     * @param user
     * @param group
     * @param u
     */
    public void addToGroup(SessionUser user,SImGroups group, SImUser u) {
        Kv<String,?> params = Kv.by("add",Lists.newArrayList(u.getUsername()));
        post("/v1/groups/" + group.getGid() + "/members", params);
    }


    /**
     * 删除用户从群组
     * @param user
     * @param group
     * @param u
     */
    public void delFromGroup(SessionUser user,SImGroups group, SImUser u) {
        Kv<String,?> params = Kv.by("remove",Lists.newArrayList(u.getUsername()));
        post( "/v1/groups/" + group.getGid() + "/members", params);
    }

    /**
     * 返回群组所有用户
     * @param group
     * @return
     */
    public List<SImUser> getUsers(SImGroups group){
        try {
            ResponseEntity<String> entity = get("/v1/groups/" + group.getGid() + "/members");
            return JSON.parseArray(entity.getBody(), SImUser.class);
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
    public boolean hasUser(SImGroups group,SImUser u){
        List<SImUser> users = getUsers(group);
        return users.stream().anyMatch(s -> s.getUsername().equals(u.getUsername()));
    }


    /**
     * 注册管理员
     * @param admin
     * @return
     */
    public SImUser addAdmin(SImUser admin){
        Kv<String,Object> params = Kv.obj("username", admin.getUsername()).set("password", admin.getPassword()).set("nickname", admin.getNickname());
        ResponseEntity<String> entity = post("/v1/admins", params);
        return getUser(admin.getUsername());
    }



}
