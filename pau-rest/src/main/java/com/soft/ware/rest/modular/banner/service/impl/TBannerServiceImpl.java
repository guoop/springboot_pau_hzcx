package com.soft.ware.rest.modular.banner.service.impl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.banner.dao.TBannerMapper;
import com.soft.ware.rest.modular.banner.model.TBanner;
import com.soft.ware.rest.modular.banner.service.TBannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TBannerServiceImpl extends BaseService<TBannerMapper,TBanner> implements TBannerService {

    @Resource
    private TBannerMapper mapper;

    @Override
    public List<TBanner> findBannerByOwner(SessionUser owner) {
        return mapper.selectList(new EntityWrapper<>(new TBanner().setIsDelete(TBanner.is_delete_0).setOwnerId(owner.getOwnerId())));
    }
}