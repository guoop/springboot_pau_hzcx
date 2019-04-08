package com.soft.ware.rest.modular.app_version.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.app_version.dao.TAppVersionMapper;
import com.soft.ware.rest.modular.app_version.model.TAppVersion;
import com.soft.ware.rest.modular.app_version.service.TAppVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TAppVersionServiceImpl extends BaseService<TAppVersionMapper,TAppVersion> implements TAppVersionService {

    @Resource
    private TAppVersionMapper mapper;

}