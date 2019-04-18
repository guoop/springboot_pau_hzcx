package com.soft.ware.rest.modular.device.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.device.dao.TDeviceMapper;
import com.soft.ware.rest.modular.device.model.TDevice;
import com.soft.ware.rest.modular.device.service.ITDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TDeviceServiceImpl extends BaseService<TDeviceMapper,TDevice> implements ITDeviceService {

    @Resource
    private TDeviceMapper mapper;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
    List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }

    @Override
    public TDevice findOne(Map<String,Object> map) throws Exception {
        return BeanMapUtils.toObject(map, TDevice.class);
    }


}