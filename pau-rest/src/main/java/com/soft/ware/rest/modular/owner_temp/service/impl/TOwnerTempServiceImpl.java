package com.soft.ware.rest.modular.owner_temp.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.owner_temp.dao.TOwnerTempMapper;
import com.soft.ware.rest.modular.owner_temp.model.TOwnerTemp;
import com.soft.ware.rest.modular.owner_temp.service.TOwnerTempService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TOwnerTempServiceImpl extends BaseService<TOwnerTempMapper,TOwnerTemp> implements TOwnerTempService {

    @Resource
    private TOwnerTempMapper mapper;

}