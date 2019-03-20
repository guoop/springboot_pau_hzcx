package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOwnerStaffMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class AuthServiceImpl extends ServiceImpl<TblOwnerStaffMapper, TblOwnerStaff> implements AuthService {

    @Resource
    private TblOwnerStaffMapper staffMapper;

    @Override
    public TblOwnerStaff findByUsername(String username) {
        return staffMapper.findStaffByPhone(username);
    }
}
