package com.soft.ware.rest.common.persistence.dao;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.HandOver;

public interface HandOverMapper extends BaseMapper<HandOver> {
	
	Object getHandOverList(Map<String,Object> map);
	
	Object getHandOverByOwner(Map<String,Object> map);

}
