package com.soft.ware.rest.modular.goods_storage.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.goods_storage.dao.TGoodsStorageMapper;
import com.soft.ware.rest.modular.goods_storage.model.TGoodsStorage;
import com.soft.ware.rest.modular.goods_storage.service.ITGoodsStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TGoodsStorageServiceImpl extends BaseService<TGoodsStorageMapper,TGoodsStorage> implements ITGoodsStorageService {

    @Resource
    private TGoodsStorageMapper mapper;


}