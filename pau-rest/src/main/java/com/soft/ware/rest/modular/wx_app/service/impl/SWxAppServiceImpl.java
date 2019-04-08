package com.soft.ware.rest.modular.wx_app.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.wx_app.dao.SWxAppMapper;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class SWxAppServiceImpl extends BaseService<SWxAppMapper,SWxApp> implements ISWxAppService {

    @Resource
    private SWxAppMapper mapper;

}