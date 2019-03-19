package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.auth.controller.dto.AuthRequest;
import com.soft.ware.rest.common.persistence.dao.SecUserMapper;
import com.soft.ware.rest.common.persistence.model.SecUser;
import com.soft.ware.rest.modular.auth.service.RestAuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class AuthServiceImpl extends ServiceImpl<SecUserMapper, SecUser> implements RestAuthService {

    @Resource
    private SecUserMapper secUserMapper;

    @Override
    public SecUser findByUsername(AuthRequest req) {
        return secUserMapper.findByUsername(req.getUserName());
    }

}
