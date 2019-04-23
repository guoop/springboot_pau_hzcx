package com.soft.ware.rest.modular.owner_staff.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.ParameterException;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.exception.ServiceExceptionEnum;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroupType;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.im.model.SImGroups;
import com.soft.ware.rest.modular.im.model.SImUser;
import com.soft.ware.rest.modular.im.service.ISImUserService;
import com.soft.ware.rest.modular.im.service.ImService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.dao.TOwnerStaffMapper;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class TOwnerStaffServiceImpl extends BaseService<TOwnerStaffMapper,TOwnerStaff> implements TOwnerStaffService {

    @Resource
    private TOwnerStaffMapper tOwnerStaffMapper;

    @Autowired
    private ITOwnerService itOwnerService;

    @Autowired
    private ImService imService;

    @Autowired
    ISImUserService imUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public TOwnerStaff findByPhone(String phone) {
        TOwnerStaff staff = new TOwnerStaff();
        staff.setPhone(phone);
        return tOwnerStaffMapper.selectOne(staff);

    }

    @Override
    public List<TOwnerStaff> selectStaffByOwnerId(String ownerId) {
        return tOwnerStaffMapper.selectStaffByOwnerId(ownerId);
    }


    @Override
    public boolean addOrUpdate(SessionUser sessionUser,  StaffEditParam param) {
        ImGroupType type = ImGroupType.STAFF;//极光小程序用户
        TOwner o = itOwnerService.selectById(sessionUser.getOwnerId());
        Integer row = 0;
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
            s =  param.update(s);
            s.setOwnerId(sessionUser.getOwnerId());
            s.setCreateTime(new Date());
            s.setId(IdGenerator.getId());
            s.setOwnerId(sessionUser.getOwnerId());
            row = tOwnerStaffMapper.insert(s);
            //添加到im群组
            if (s.getPassword() != null && s.getPassword().length() > 10) {
                try {
                    imService.syncUsers(sessionUser,o, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } else {
            //修改店员信息
            param.update(s);
            s.setPhone(param.getPhone());
            String password = "^a9682150f2e011e8uy572f1cf5acecff-" + param.getPhone() + "-" + param.getPassword() + "$";
            password = Base64.getEncoder().encodeToString(DigestUtils.updateDigest(DigestUtils.getMd5Digest(), password).digest());
            s.setPassword(password);
            s.setCategoryList(param.getCategoryList());
            s.setFunctionList(param.getFunctionList());
            s.setName(param.getName());
            s.setStatus(Integer.valueOf(param.getStatus()));
            //全部更新
            if (!s.getOwnerId().equals(sessionUser.getOwnerId())) {
                throw new PauException(BizExceptionEnum.ERROR);
            }
            row = tOwnerStaffMapper.updateAllColumnById(s);
            String keyPrefix = "user:" + s.getPhone() + ":*";
            Set<String> keys = redisTemplate.keys(keyPrefix);
        }
        if(row > 0){
            if(s.getFunctionList().contains("goodsMan")||s.getFunctionList().contains("configOrderPhone")){
                try {
                    imService.syncUsers(sessionUser,o,s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }



           /* if (TOwnerStaff.status_0 == Integer.valueOf(param.getStatus())) {
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
            if (row > 0) {
                return true;
            }*/
            return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delStaff(Map<String, Object> map,SessionUser sessionUser) {

            TOwnerStaff staff = new TOwnerStaff();
            staff.setId(map.get("id").toString());
            staff.setOwnerId(sessionUser.getOwnerId());
            staff.setPhone(map.get("phone").toString());
            staff.setStatus(2);
            if(tOwnerStaffMapper.updateById(staff) > 0){
                try {
                    imService.syncUsers(sessionUser,itOwnerService.selectById(sessionUser.getOwnerId()),staff);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        return false;
    }


}

