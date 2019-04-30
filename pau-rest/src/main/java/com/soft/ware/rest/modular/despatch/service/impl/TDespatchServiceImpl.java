package com.soft.ware.rest.modular.despatch.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.despatch.dao.TDespatchMapper;
import com.soft.ware.rest.modular.despatch.model.TDespatch;
import com.soft.ware.rest.modular.despatch.service.TDespatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TDespatchServiceImpl extends BaseService<TDespatchMapper,TDespatch> implements TDespatchService {

    @Resource
    private TDespatchMapper mapper;

}