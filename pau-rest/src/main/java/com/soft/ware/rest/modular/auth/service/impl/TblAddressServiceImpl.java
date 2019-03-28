package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblAddressMapper;
import com.soft.ware.rest.common.persistence.model.TblAddress;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TblAddressServiceImpl extends BaseService<TblAddressMapper,TblAddress> implements TblAddressService {

    @Resource
    private TblAddressMapper addressMapper;

    @Override
    public List<TblAddress> findAll(SessionUser user) {
        return addressMapper.findAll(user);
    }

    @Override
    public TblAddress findById(SessionUser user, long id) {
        return addressMapper.selectOne(new TblAddress().setId(id).setOwner(user.getOpenId()));
    }

    @Override
    public boolean addAddress(SessionUser user,TblAddress address) {
        //清空默认收获地址
        if (TblAddress.is_default_1.equals(address.getIsDefault())) {
            addressMapper.deleteDefaultAddress(user);
        }
        Integer insert = addressMapper.insert(address);
        return insert == 1;
    }

    @Override
    public boolean updateAddress(SessionUser user,TblAddress address) {
        //清空默认收货地址
        if (TblAddress.is_default_1.equals(address.getIsDefault())) {
            addressMapper.deleteDefaultAddress(user);
        }
        Integer integer = addressMapper.updateById(address);
        return integer == 1;
    }

    @Override
    public boolean deleteById(SessionUser user,TblAddress address){
        address.setIsDeleted(TblAddress.is_deleted_1);
        Integer row = addressMapper.update(address, new EntityWrapper<>(new TblAddress().setId(address.getId()).setOwner(user.getOpenId())));
        return row == 1;
    }
}