package com.soft.ware.rest.modular.banner.service;
import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.banner.model.TBanner;

import java.util.List;

public interface TBannerService extends IService<TBanner> {

    List<TBanner> findBannerByOwner(SessionUser owner);
}
