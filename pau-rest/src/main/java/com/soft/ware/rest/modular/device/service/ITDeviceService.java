package com.soft.ware.rest.modular.device.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.device.model.TDevice;

import java.util.List;
import java.util.Map;

public interface ITDeviceService extends IService<TDevice> {

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

    TDevice findOne(Map<String, Object> map) throws Exception;
}
