package com.soft.ware.rest.modular.owner.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.owner.dao.TOwnerMapper;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商户信息 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TOwnerServiceImpl extends ServiceImpl<TOwnerMapper, TOwner> implements ITOwnerService {

    private TOwnerMapper mapper;

    @Override
    public TOwner findByAppId(String appId) {
        //return mapper.selectOne(new TOwner().setAppId(appId));
        return null;
    }
}
