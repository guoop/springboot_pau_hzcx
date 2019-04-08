package com.soft.ware.rest.modular.owner_config.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.owner_config.dao.TOwnerConfigMapper;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import com.soft.ware.rest.modular.owner_config.service.TOwnerConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TOwnerConfigServiceImpl extends BaseService<TOwnerConfigMapper,TOwnerConfig> implements TOwnerConfigService {

    @Resource
    private TOwnerConfigMapper mapper;

}