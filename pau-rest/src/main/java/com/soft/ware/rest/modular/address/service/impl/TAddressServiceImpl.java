package com.soft.ware.rest.modular.address.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.address.dao.TAddressMapper;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.address.service.ITAddressService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TAddressServiceImpl extends BaseService<TAddressMapper,TAddress> implements  ITAddressService {

    @Resource
    private TAddressMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? Kv.toKv(maps.get(0)) : null;
    }

    @Override
    public boolean addAddress(SessionUser user, TAddress address) {
        //清空默认收获地址
        address.setId(IdGenerator.getId());
        address.setCreatedTime(new Date());
        address.setIsDelete(TAddress.is_delete_0);
        if (TAddress.is_default_1.equals(address.getIsDefault())) {
            mapper.deleteDefaultAddress(user);
        }
        Integer insert = mapper.insert(address);
        return insert == 1;
    }

    @Override
    public boolean updateAddress(SessionUser user, TAddress address) {
        //更新地址就是添加地址
        Integer integer = mapper.updateById(address);
        this.deleteById(user,address);
        address.setId(null);
        addAddress(user, address);
        return integer == 1;
    }

    @Override
    public boolean deleteById(SessionUser user, TAddress address){
        address.setIsDelete(TAddress.is_delete_1);
        address.setDeleteTime(new Date());
        Integer row = mapper.update(address, new EntityWrapper<>(new TAddress().setId(address.getId()).setCreater(user.getOpenId())));
        return row == 1;
    }

    @Override
    public TAddress findById(SessionUser user, String addressId) throws Exception {
        return BeanMapUtils.toObject(findMap(Kv.obj("id", addressId).set("creater", user.getOpenId())), TAddress.class);
    }


}