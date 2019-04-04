package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblRepositoryMapper;
import com.soft.ware.rest.common.persistence.model.TblRepository;
import com.soft.ware.rest.modular.auth.service.TblRepositoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
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

    @Override
    public List<TblRepository> findByCode(String code) {
        return tblRepositoryMapper.selectList(new EntityWrapper<>(new TblRepository().setCode(code).setIsDelete(TblRepository.is_delete_0)));
    }

}