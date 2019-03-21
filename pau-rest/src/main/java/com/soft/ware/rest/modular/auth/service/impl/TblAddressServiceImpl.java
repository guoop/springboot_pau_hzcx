package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblAddressMapper;
import com.soft.ware.rest.common.persistence.model.TblAddress;
import com.soft.ware.rest.modular.auth.service.TblAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblAddressServiceImpl extends ServiceImpl<TblAddressMapper,TblAddress> implements TblAddressService {

    @Resource
    private TblAddressMapper tblAddressMapper;

}