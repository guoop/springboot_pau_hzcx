package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class TblAddressServiceImpl extends ServiceImpl<TblAddressMapper,TblAddress> implements TblAddressService {

    @Resource
    private TblAddressMapper addressMapper;

    @Override
    public List<TblAddress> findAll(SessionUser user) {
        return addressMapper.findAll(user);
    }

    @Override
    public TblAddress findById(SessionUser user, long id) {
        return addressMapper.selectOne(new TblAddress().setId(id).setOwner(user.getId()));
    }

    @Override
    public boolean addAddress(SessionUser user,TblAddress address) {
        //清空默认收获地址
        if (address.getIsDefault().equals(TblAddress.is_default_1)) {
            addressMapper.deleteDefaultAddress(user);
        }
        Integer insert = addressMapper.insert(address);
        return insert == 1;
    }

    @Override
    public boolean updateAddress(SessionUser user,TblAddress address) {
        //清空默认收货地址
        if (address.getIsDefault().equals(TblAddress.is_default_1)) {
            addressMapper.deleteDefaultAddress(user);
        }
        Integer integer = addressMapper.updateById(address);
        return integer == 1;
    }
}