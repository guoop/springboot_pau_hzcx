package com.soft.ware.rest.modular.owner_staff.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.im.service.ImService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.dao.TOwnerStaffMapper;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Set;

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
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public TOwnerStaff findByPhone(String phone) {
        TOwnerStaff staff = new TOwnerStaff();
        staff.setPhone(phone);
        return tOwnerStaffMapper.selectOne(staff);

    }

    @Override
    public TOwnerStaff selectStaffByOwnerId(String ownerId) {
        return tOwnerStaffMapper.selectStaffByOwnerId(ownerId);
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
            s =  param.update(s);
            s.setOwnerId(sessionUser.getOwnerId());
            s.setCreateTime(new Date());
            s.setId(IdGenerator.getId());
            s.setOwnerId(sessionUser.getOwnerId());
            long row = tOwnerStaffMapper.insertOwnerStaff(s);
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
            return  true;
        } else {
            //修改店员信息
            param.update(s);
            //全部更新
            if (!s.getOwnerId().equals(sessionUser.getOwnerId())) {
                throw new PauException(BizExceptionEnum.ERROR);
            }
            Integer row = tOwnerStaffMapper.updateAllColumnById(s);
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



}

