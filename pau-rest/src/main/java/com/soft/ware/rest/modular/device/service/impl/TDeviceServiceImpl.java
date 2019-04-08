package com.soft.ware.rest.modular.device.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.device.dao.TDeviceMapper;
import com.soft.ware.rest.modular.device.model.TDevice;
import com.soft.ware.rest.modular.device.service.TDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TDeviceServiceImpl extends BaseService<TDeviceMapper,TDevice> implements TDeviceService {

    @Resource
    private TDeviceMapper mapper;

}