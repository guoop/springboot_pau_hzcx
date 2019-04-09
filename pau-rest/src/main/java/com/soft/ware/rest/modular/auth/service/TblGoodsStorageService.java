package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblGoodsStorage;

import java.util.Map;

public interface TblGoodsStorageService extends IService<TblGoodsStorage> {

	boolean adjustGoodsRepository(Map<String,Object> map);
}
