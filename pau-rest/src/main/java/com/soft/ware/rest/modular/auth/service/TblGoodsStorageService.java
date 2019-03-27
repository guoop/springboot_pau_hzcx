package com.soft.ware.rest.modular.auth.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblGoodsStorage;

public interface TblGoodsStorageService extends IService<TblGoodsStorage> {

	boolean adjustGoodsRepository(Map<String,Object> map);
}
