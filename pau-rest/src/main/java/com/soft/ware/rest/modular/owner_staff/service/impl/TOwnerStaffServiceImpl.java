package com.soft.ware.rest.modular.owner_staff.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.owner_staff.dao.TOwnerStaffMapper;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TOwnerStaffServiceImpl extends BaseService<TOwnerStaffMapper,TOwnerStaff> implements TOwnerStaffService {

    @Resource
    private TOwnerStaffMapper mapper;

    @Override
    public TOwnerStaff findByPhone(String phone) {
        TOwnerStaff staff = new TOwnerStaff();
        staff.setPhone(phone);
        return mapper.selectOne(staff);

    }
}