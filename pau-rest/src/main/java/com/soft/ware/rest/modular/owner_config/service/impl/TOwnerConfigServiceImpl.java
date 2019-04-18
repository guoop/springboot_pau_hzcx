package com.soft.ware.rest.modular.owner_config.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.owner_config.dao.TOwnerConfigMapper;
import com.soft.ware.rest.modular.owner_config.model.TOwnerConfig;
import com.soft.ware.rest.modular.owner_config.service.ITOwnerConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TOwnerConfigServiceImpl extends BaseService<TOwnerConfigMapper,TOwnerConfig> implements ITOwnerConfigService {

    @Resource
    private TOwnerConfigMapper mapper;

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
    public boolean updateOwnerConfigInfo(Map<String, Object> map, SessionUser sessionUser) {
        map.put("ownerId",sessionUser.getOwnerId());
        return  mapper.updateOwnerConfigInfo(map);
    }


}