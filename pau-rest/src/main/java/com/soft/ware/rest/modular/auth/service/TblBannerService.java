package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblBanner;

import java.util.List;

public interface TblBannerService extends IService<TblBanner> {

    List<TblBanner> findBannerByOwner(String owner);
}
