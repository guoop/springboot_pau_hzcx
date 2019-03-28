package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblRepositoryMapper;
import com.soft.ware.rest.common.persistence.model.TblRepository;
import com.soft.ware.rest.modular.auth.service.TblRepositoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
@Service
@Transactional
public class TblRepositoryServiceImpl extends BaseService<TblRepositoryMapper,TblRepository> implements TblRepositoryService {

    @Resource
    private TblRepositoryMapper tblRepositoryMapper;

	@Override
	public TblRepository getGoodsRepositoryByCode(Map<String, Object> map) {
		return tblRepositoryMapper.getGoodsRepositoryByCode(map);
	}
    
}