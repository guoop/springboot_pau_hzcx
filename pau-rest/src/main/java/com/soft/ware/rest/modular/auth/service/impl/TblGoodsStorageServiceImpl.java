package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.persistence.dao.TblGoodsStorageMapper;
import com.soft.ware.rest.common.persistence.model.TblGoodsStorage;
import com.soft.ware.rest.modular.auth.service.TblGoodsStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
@Service
@Transactional
public class TblGoodsStorageServiceImpl extends BaseService<TblGoodsStorageMapper,TblGoodsStorage> implements TblGoodsStorageService {

    @Resource
    private TblGoodsStorageMapper tblGoodsStorageMapper;

	@Override
	public boolean adjustGoodsRepository(Map<String, Object> map) {
		Integer updateNum = 0;
		if(ToolUtil.isNotEmpty(map)){
			TblGoodsStorage storage = new TblGoodsStorage();
			if(ToolUtil.isNotEmpty(map.get("id").toString())){
				storage.setId(Long.valueOf(map.get("id").toString()));
			}
			if(ToolUtil.isNotEmpty(map.get("single_price").toString())){
				storage.setSinglePrice(BigDecimal.valueOf(Double.valueOf(map.get("single_price").toString())));
			}
			if(ToolUtil.isNotEmpty(map.get("in_amount").toString())){
				storage.setInAmount(BigDecimal.valueOf(Double.valueOf(map.get("in_amount").toString())));
			}
			if(ToolUtil.isNotEmpty(map.get("in_money").toString())){
				storage.setInMoney(BigDecimal.valueOf(Double.valueOf(map.get("in_money").toString())));
			}
			if(ToolUtil.isNotEmpty(map.get("baseline").toString())){
				storage.setBaseline(BigDecimal.valueOf(Double.valueOf(map.get("baseline").toString())));
			}
			if(ToolUtil.isNotEmpty(map.get("in_at").toString())){
				storage.setInAt(DateUtil.parseTime(map.get("in_at").toString()));
			}
			if(ToolUtil.isNotEmpty(map.get("owner").toString())){
				storage.setOwner(map.get("owner").toString());
			}
			updateNum = tblGoodsStorageMapper.updateById(storage);
			if(updateNum != 0){
				return true;
			}
		}
		return false;
	}

    
}