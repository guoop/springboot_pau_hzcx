package com.soft.ware.rest.modular.icon.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.icon.dao.TIconMapper;
import com.soft.ware.rest.modular.icon.model.TIcon;
import com.soft.ware.rest.modular.icon.service.TIconService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TIconServiceImpl extends BaseService<TIconMapper,TIcon> implements TIconService {

    @Resource
    private TIconMapper mapper;

}