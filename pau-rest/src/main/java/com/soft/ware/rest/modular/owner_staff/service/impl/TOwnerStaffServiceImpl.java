package com.soft.ware.rest.modular.owner_staff.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroupType;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.PasswordUtils;
import com.soft.ware.rest.modular.im.service.ISImUserService;
import com.soft.ware.rest.modular.im.service.ImService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.dao.TOwnerStaffMapper;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.ITOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class TOwnerStaffServiceImpl extends BaseService<TOwnerStaffMapper,TOwnerStaff> implements ITOwnerStaffService {

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


    @Resource
    private TOwnerStaffMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? null : Kv.toKv(maps.get(0));
    }

    @Override
    public TOwnerStaff findOne(Map<String,Object> map) throws Exception {
        return BeanMapUtils.toObject(map, TOwnerStaff.class);
    }

    @Override
    public List<TOwnerStaff> findList(Map<String,Object> params) throws Exception {
        List<Map<String, Object>> maps = findMaps(params);
        return BeanMapUtils.toObject(maps, TOwnerStaff.class);
    }


    @Override
    public TOwnerStaff findByPhone(String phone) {
        TOwnerStaff staff = new TOwnerStaff();
        staff.setPhone(phone);
        return tOwnerStaffMapper.selectOne(staff);

    }

    @Override
    public TOwnerStaff findByLoginName(String phone) {
        return selectOne(new EntityWrapper<>(new TOwnerStaff().setPhone(phone)).ne("status", TOwnerStaff.status_2));
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
        TOwnerStaff p = this.findByLoginName(param.getPhone());
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
            s.setPassword(PasswordUtils.encode(param.getPhone(), param.getPassword()));
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
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delStaff(Map<String, Object> map,SessionUser sessionUser) throws Exception {
        TOwnerStaff staff = new TOwnerStaff();
        staff.setId(map.get("id").toString());
        staff.setOwnerId(sessionUser.getOwnerId());
        staff.setPhone(map.get("phone").toString());
        staff.setStatus(2);
        updateById(staff);
        imService.syncUsers(sessionUser,itOwnerService.selectById(sessionUser.getOwnerId()),staff);
        return true;
    }


}

