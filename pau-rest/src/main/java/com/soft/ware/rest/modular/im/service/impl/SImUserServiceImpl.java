package com.soft.ware.rest.modular.im.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.im.dao.SImUserMapper;
import com.soft.ware.rest.modular.im.model.SImUser;
import com.soft.ware.rest.modular.im.service.ISImUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SImUserServiceImpl extends BaseService<SImUserMapper,SImUser> implements  ISImUserService {

    @Resource
    private SImUserMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }


    public SImUser findByUsername(String username){
        SImUser user = mapper.selectOne(new SImUser().setUsername(username));
        return user;
    }


    @Override
    public boolean saveOrUpdate(SessionUser user, SImUser u) {
        SImUser old = findByUsername(u.getUsername());
        u.setOwnerId(user.getOwnerId());
        int row;
        if (old == null) {
            u.setId(IdGenerator.getId());
            u.setCreateTime(new Date());
            u.setCreater(user.getName());
            row = mapper.insert(u);
        } else {
            u.setOwnerId(user.getOwnerId());
            u.setId(old.getId());
            row = mapper.updateById(u);
        }

        return row == 0;

    }

    @Override
    public boolean deleteByUsername(SessionUser user, String username) {
        SImUser u = findByUsername(username);
        if (u != null) {
            return delete(new EntityWrapper<>(new SImUser().setId(u.getId()).setOwnerId(user.getOwnerId())));
        }
        return false;
    }


}