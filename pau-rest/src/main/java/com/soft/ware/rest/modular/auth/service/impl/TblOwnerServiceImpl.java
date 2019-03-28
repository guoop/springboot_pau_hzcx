package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOwnerMapper;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class TblOwnerServiceImpl extends BaseService<TblOwnerMapper,TblOwner> implements TblOwnerService {

    @Resource
    private TblOwnerMapper ownerMapper;

    @Override
    public TblOwner find(SessionUser user) {
        return ownerMapper.findOwnerByOwner(user.getOwner());
    }


    @Override
    public TblOwner find(SessionUser customer, boolean all) {
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

    @Override
    public TblOwner find(String owner) {
        return ownerMapper.findOwnerByOwner(owner);
    }

    @Override
    public TblOwner findByAppId(String appId) {
        return ownerMapper.selectOne(new TblOwner().setAppId(appId));
    }

}
