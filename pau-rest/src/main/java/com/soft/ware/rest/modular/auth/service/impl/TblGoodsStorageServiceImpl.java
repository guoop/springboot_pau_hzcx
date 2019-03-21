package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblGoodsStorageMapper;
import com.soft.ware.rest.common.persistence.model.TblGoodsStorage;
import com.soft.ware.rest.modular.auth.service.TblGoodsStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblGoodsStorageServiceImpl extends ServiceImpl<TblGoodsStorageMapper,TblGoodsStorage> implements TblGoodsStorageService {

    @Resource
    private TblGoodsStorageMapper tblGoodsStorageMapper;

}