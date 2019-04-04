package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblRepository;

import java.util.List;
import java.util.Map;

public interface TblRepositoryService extends IService<TblRepository> {

	TblRepository getGoodsRepositoryByCode(Map<String,Object> map);

    List<TblRepository> findByCode(String code);
}
