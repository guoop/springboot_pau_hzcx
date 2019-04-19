package com.soft.ware.rest.modular.owner_staff.service.impl;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.ImGroups;
import com.soft.ware.rest.common.persistence.model.ImUser;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroupType;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.service.ImGroupsService;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.ImUserService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.auth.util.WXContants;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.dao.TOwnerStaffMapper;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.*;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

@Service
@Transactional
public class TOwnerStaffServiceImpl extends BaseService<TOwnerStaffMapper,TOwnerStaff> implements TOwnerStaffService {

    @Resource
    private TOwnerStaffMapper mapper;
    @Autowired
    private ITOwnerService itOwnerService;

    @Autowired
    private RestTemplate restTemplate;

    private String password = "Hzcx-owner";

    @Autowired
    private ImUserService imUserService;

    @Autowired
    private ImGroupsService groupsService;

    @Autowired
    private ImService imService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public TOwnerStaff findByPhone(String phone) {
        TOwnerStaff staff = new TOwnerStaff();
        staff.setPhone(phone);
        return mapper.selectOne(staff);

    }

    @Override
    public TOwnerStaff selectStaffByOwnerId(String ownerId) {
        return mapper.selectStaffByOwnerId(ownerId);
    }

    @Override
    public boolean addOrUpdate(SessionUser sessionUser,  StaffEditParam param) {

        TOwner o = itOwnerService.selectById(sessionUser.getOwnerId());
        if ( param.getPhone().equals(sessionUser.getPhone())) {
            throw new PauException(BizExceptionEnum.PHONE_EXISTS);
        }
        TOwnerStaff p = this.findByPhone(param.getPhone());
        TOwnerStaff s = null;
        if (ToolUtil.isEmpty(param.getId())) {
            if (p != null) {
                throw new PauException(BizExceptionEnum.PHONE_EXISTS);
            }
        }else {
            s = this.selectById(param.getId());

            if (p != null && !p.getId().equals(s.getId())) {
                throw new PauException(BizExceptionEnum.PHONE_EXISTS);
            }
        }
        if (s == null) {
            //添加店员信息
            s = new TOwnerStaff();
            s.setOwnerId(sessionUser.getOwnerId());
            s.setCreateTime(new Date());
            param.update(s);
            s.setOwnerId(sessionUser.getOwnerId());
            s.setCreateTime(new Date());
            Integer row = mapper.insert(s);
            //添加到im群组
            if (s.getPassword() != null && s.getPassword().length() > 10) {
                try {
                    imService.syncUsers(sessionUser,o, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (row != 1) {
                throw new PauException(BizExceptionEnum.ADD_ERROR);
            }
        } else {
            //修改店员信息
            param.update(s);
            //全部更新
            if (!s.getOwnerId().equals(sessionUser.getOwnerId())) {
                throw new PauException(BizExceptionEnum.ERROR);
            }
            Integer row = mapper.updateAllColumnById(s);
            String keyPrefix = "user:" + s.getPhone() + ":*";
            Set<String> keys = redisTemplate.keys(keyPrefix);
            try {
                imService.syncUsers(sessionUser, o, s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TOwnerStaff.status_0.equals(param.getStatus())) {
                //启用店员信息
                //todo yancc 可能还需要更新im群组信息
                for (String key : keys) {
                    try {
                        redisTemplate.opsForHash().putAll(key, BeanMapUtils.toMap(s,true));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // 禁用店员信息
                //todo yancc 可能还需要删除im群组信息
                redisTemplate.delete(keys);
            }
            if (row != 1) {
                throw new PauException(BizExceptionEnum.UPDATE_ERROR);
            }
        }


        return false;
    }

    @Override
    public void syncUsers(SessionUser sessionUser,TOwner tOwner, TOwnerStaff... tOwnerStaff) {
        ImGroupType type = ImGroupType.STAFF;//极光小程序用户
        List<Kv<String, ?>> params = Lists.newArrayList();
        String username;
        for (TOwnerStaff s : tOwnerStaff) {
            username = ParamUtils.buildImUserName(s,type);
            //username = buildUsername(s, type);
            ImUser u = getUser(username);
            if (TOwnerStaff.status_0.equals(s.getStatus())) {
                //用户状态正常
                if (u == null) {
                    //注册用户
                    params.add(Kv.by("username", username).set("password", password).set("nickname", ToolUtil.isEmpty(s.getName()) ? s.getPhone() : s.getName()));
                    u = addUser(sessionUser, params);

                    imUserService.saveOrUpdate(sessionUser,u);
                    ImGroups g = requireOwnerGroup(sessionUser, tOwner, type);
                    if (!hasUser(g, u)) {
                        //添加到群组
                        addToGroup(sessionUser, g, u);
                        groupsService.saveOrUpdate(sessionUser,g);
                    }
                    logger.info("极光用户:{}被添加",username);
                } else {
                    //更新用户
                    Kv<String,?> kv = Kv.by("username", username).set("password", password).set("nickname", ToolUtil.isEmpty(s.getName()) ? s.getPhone() : s.getName());
                    u.setOwnerId(sessionUser.getOwnerId());
                    try {
                        updateUser(sessionUser,username, kv);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    u = getUser(username);
                    imUserService.saveOrUpdate(sessionUser,u);
                    logger.info("极光用户:{}被更新",username);
                    u = getUser(username);
                    ImGroups g = requireOwnerGroup(sessionUser, tOwner, type);
                    if (!hasUser(g, u)) {
                        //添加到群组
                        addToGroup(sessionUser, g, u);
                        groupsService.saveOrUpdate(sessionUser,g);
                    }
                }

            } else {
                //状态异常删除用户
                username = ParamUtils.buildImUserName(s, type);
                if (u != null) {
                    ImGroups g = requireOwnerGroup(sessionUser, tOwner, type);
                    if (g != null) {
                        //delFromGroup(user,g,u);//经过测试，删除用户时极光会自动删除他所在的群组
                        groupsService.deleteByUsername(sessionUser, g.getOwnerUsername());
                    }
                    try {
                        delUser(username);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    imUserService.deleteByUsername(sessionUser,username);
                }
                logger.info("极光用户:{}被删除",username);
            }
        }


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
     * 查询这个群组是否包含这个用户
     * @param group
     * @param u
     * @return
     */
    public boolean hasUser(ImGroups group,ImUser u){
        List<ImUser> users = getUsers(group);
        return users.stream().anyMatch(s -> s.getUsername().equals(u.getUsername()));
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
     * 获取群主信息
     * @param owner
     * @return
     */
    private ImGroups requireOwnerGroup(SessionUser user,TOwner owner,ImGroupType type){
        String username = ParamUtils.buildOwnerGroupUsername(owner,type);
        ImUser u = getUser(username);
        if (u == null) {
            ParamUtils.buildOwnerGroupUsername(owner,type);
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
     * 创建群组
     * @param user
     * @param owner
     * @param type 群组类型
     * @return
     */
    private ImGroups addGroup(SessionUser user, TOwner owner,ImGroupType type){
        String username = ParamUtils.buildOwnerGroupUsername(owner,type);
        Kv<String, Object> params = Kv.obj().set("owner_username", username).set("name", owner.getName()).set("desc", type.getDesc()).set("members_username", new String[]{});
        ResponseEntity<String> entity = post("/v1/groups/", params);
        ImGroups imGroups = JSON.parseObject(entity.getBody(), ImGroups.class);
        if (imGroups != null) {
            imGroups.setOwnerUsername(username);
        }
        return imGroups;
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
     * 发送put请求
     * @param path
     * @param params
     * @return
     */
    private void put(String path, Kv<String,?> params)  {
        HttpEntity<Object> http = new HttpEntity<>(params, getJpushHeaders());
        restTemplate.put(WXContants.JG_GATEWAY + path, http);
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
     * 获取群主信息
     * @param owner
     * @return
     */
   /* private ImGroups requireOwnerGroup(SessionUser user,TblOwner owner,ImGroupType type){
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
    }*/


}

