package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.Customer;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.util.Page;

import java.util.List;
import java.util.Map;

public interface TblGoodsService extends IService<TblGoods> {

    List<Map> findPage(TblOwnerStaff staff, Page page,GoodsPageParam param);

    List<Map> findPage(Customer customer, Page page, GoodsPageParam param);

    TblGoods findById(Long id);
}
