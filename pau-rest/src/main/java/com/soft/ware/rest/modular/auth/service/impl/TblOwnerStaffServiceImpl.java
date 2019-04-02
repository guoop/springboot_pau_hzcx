package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblOwnerStaffMapper;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.service.TblOwnerStaffService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class TblOwnerStaffServiceImpl extends BaseService<TblOwnerStaffMapper,TblOwnerStaff> implements TblOwnerStaffService {

    @Resource
    private TblOwnerStaffMapper mapper;

    @Autowired
    private TblOwnerService tblOwnerService;

    @Autowired
    private ImService imService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public TblOwnerStaff findByPhone(String phone) {
        return mapper.selectOne(new TblOwnerStaff().setPhone(phone));
    }

    @Override
    public TblOwnerStaff find(SessionUser user) {
        return mapper.selectOne(new TblOwnerStaff().setId(Integer.valueOf(user.getId())));
    }

    @Override
    public boolean saveOrUpdate(SessionUser user, StaffEditParam param) throws Exception {
        TblOwner o = tblOwnerService.findByPhone(param.getPhone());
        if (o != null && param.getPhone().equals(o.getPhone())) {
            throw new PauException(BizExceptionEnum.PHONE_EXISTS);
        }
        TblOwnerStaff p = this.findByPhone(param.getPhone());
        TblOwnerStaff s = null;
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
            s = new TblOwnerStaff();
            param.update(s);
            s.setOwner(user.getOwner());
            s.setCreatedAt(new Date());
            Integer row = mapper.insert(s);
            //添加到im群组
            if (s.getPassword() != null && s.getPassword().length() > 10) {
                imService.addOrUpdateUsers(user, s);
            }
            if (row != 1) {
                throw new PauException(BizExceptionEnum.ADD_ERROR);
            }
        } else {
            //修改店员信息
            param.update(s);
            //全部更新
            if (!s.getOwner().equals(user.getOwner())) {
                throw new PauException(BizExceptionEnum.ERROR);
            }
            Integer row = mapper.updateAllColumnById(s);
            String keyPrefix = "user:" + s.getPhone() + ":*";
            Set<String> keys = redisTemplate.keys(keyPrefix);
            if (TblOwnerStaff.status_0.equals(param.getStatus())) {
                //启用店员信息
                //todo yancc 可能还需要更新im群组信息
                for (String key : keys) {
                    redisTemplate.opsForHash().putAll(key, BeanMapUtils.toMap(s,true));
                }
                imService.addOrUpdateUsers(user, s);
            } else {
                // 禁用店员信息
                //todo yancc 可能还需要删除im群组信息
                redisTemplate.delete(keys);
            }
            if (row != 1) {
                throw new PauException(BizExceptionEnum.UPDATE_ERROR);
            }
        }

        return true;
    }

    @Override
    public List<TblOwnerStaff> selectAll(SessionUser user) {
        return mapper.selectList(new EntityWrapper<TblOwnerStaff>().eq("owner", user.getOwner()).ne("status", TblOwnerStaff.status_2));
    }

}