package com.soft.ware.rest.modular.auth.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblRepository;

public interface TblRepositoryService extends IService<TblRepository> {

	TblRepository getGoodsRepositoryByCode(Map<String,Object> map);
	
}
