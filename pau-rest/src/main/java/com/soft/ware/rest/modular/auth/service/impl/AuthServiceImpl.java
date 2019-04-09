package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOwnerStaffMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.service.AuthService;
import com.soft.ware.rest.modular.owner_staff.dao.TOwnerStaffMapper;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class AuthServiceImpl extends BaseService<TOwnerStaffMapper, TOwnerStaff> implements AuthService {

    @Resource
    private TOwnerStaffMapper staffMapper;

    @Override
    public TOwnerStaff findByUsername(String username) {
        TOwnerStaff  staff = new TOwnerStaff();
        staff.setPhone(username);
        return staffMapper.selectOne(staff);
    }
}
