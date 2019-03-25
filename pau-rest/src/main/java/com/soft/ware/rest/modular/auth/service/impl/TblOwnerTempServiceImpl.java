package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOwnerTempMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerTemp;
import com.soft.ware.rest.modular.auth.service.TblOwnerTempService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOwnerTempServiceImpl extends ServiceImpl<TblOwnerTempMapper,TblOwnerTemp> implements TblOwnerTempService {

    @Resource
    private TblOwnerTempMapper tblOwnerTempMapper;

}