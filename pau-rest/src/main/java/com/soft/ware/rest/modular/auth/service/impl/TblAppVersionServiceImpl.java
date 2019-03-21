package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblAppVersionMapper;
import com.soft.ware.rest.common.persistence.model.TblAppVersion;
import com.soft.ware.rest.modular.auth.service.TblAppVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblAppVersionServiceImpl extends ServiceImpl<TblAppVersionMapper,TblAppVersion> implements TblAppVersionService {

    @Resource
    private TblAppVersionMapper appVersionMapper;

    @Override
    public TblAppVersion findLast(String platformCode) {
        return appVersionMapper.findLast(platformCode);
    }
}