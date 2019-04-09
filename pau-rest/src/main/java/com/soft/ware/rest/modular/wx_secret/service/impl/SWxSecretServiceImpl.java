package com.soft.ware.rest.modular.wx_secret.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_secret.dao.SWxSecretMapper;
import com.soft.ware.rest.modular.wx_secret.model.SWxSecret;
import com.soft.ware.rest.modular.wx_secret.service.ISWxSecretService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class SWxSecretServiceImpl extends BaseService<SWxSecretMapper,SWxSecret> implements  ISWxSecretService {

    @Resource
    private SWxSecretMapper mapper;

    @Override
    public SWxSecret find(SWxApp app) {
        return selectOne(new EntityWrapper<>(new SWxSecret().setOwnerId(app.getOwnerId())));
    }
}