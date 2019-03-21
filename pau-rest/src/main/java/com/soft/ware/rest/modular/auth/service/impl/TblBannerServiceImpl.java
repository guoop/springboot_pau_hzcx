package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblBannerMapper;
import com.soft.ware.rest.common.persistence.model.TblBanner;
import com.soft.ware.rest.modular.auth.service.TblBannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblBannerServiceImpl extends ServiceImpl<TblBannerMapper,TblBanner> implements TblBannerService {

    @Resource
    private TblBannerMapper tblBannerMapper;

}