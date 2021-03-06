package com.soft.ware.rest.modular.owner_config.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;

import java.util.List;
import java.util.Map;

public interface ITOwnerConfigService extends IService<TOwnerConfig> {

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Kv<String,Object> findMap(Map<String,Object> map);

    boolean updateOwnerConfig(Map<String,Object> map);


}
