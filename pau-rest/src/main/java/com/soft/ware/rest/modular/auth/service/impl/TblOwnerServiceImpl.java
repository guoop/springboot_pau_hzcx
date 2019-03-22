package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOwnerMapper;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.Customer;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class TblOwnerServiceImpl extends ServiceImpl<TblOwnerMapper,TblOwner> implements TblOwnerService {

    @Resource
    private TblOwnerMapper ownerMapper;

    @Override
    public TblOwner find(TblOwnerStaff user) {
        return ownerMapper.findOwnerByOwner(user.getOwner());
    }

    public TblOwner find(Customer customer) {
        return find(customer,false);
    }

    @Override
    public TblOwner find(Customer customer, boolean all) {
        TblOwner o = ownerMapper.findOwnerByOwner(customer.getOwner());
        if (!all) {
            o.setId(null);
            o.setAppId(null);
            o.setAppSecret(null);
            o.setShopId(null);
            o.setShopSecret(null);
            o.setDefaultDesc(null);
            o.setDefaultRefundReason(null);
            o.setOrderPhone(null);
        }
        return o;
    }

}
