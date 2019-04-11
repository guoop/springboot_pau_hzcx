package com.soft.ware.rest.modular.owner.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.owner.dao.TOwnerMapper;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.wx_app.model.SWxApp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户信息 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TOwnerServiceImpl extends BaseService<TOwnerMapper, TOwner> implements ITOwnerService {

    @Resource
    private TOwnerMapper mapper;

    @Override
    public TOwner find(SWxApp app) {
        return mapper.selectOne(new TOwner().setId(app.getOwnerId()));
    }

    @Override
        public TOwner findByAppId(String appId) {
        //return mapper.selectOne(new TOwner().setAppId(appId));

        return null;
    }

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMap(map);
    }

    @Override
    public Map<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.isEmpty() ? null : maps.get(0);
    }

    @Override
    public TOwner find(SessionUser user) throws Exception {
        return BeanMapUtils.toObject(findMap(Kv.by("ownerId", user.getOwnerId())), TOwner.class);
    }
}
