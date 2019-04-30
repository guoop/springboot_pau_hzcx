package com.soft.ware.rest.modular.handover.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.handover.model.HandOver;

import java.util.Map;

public interface HandOverMapper extends BaseMapper<HandOver> {
	
	Object getHandOverList(Map<String,Object> map);
	
	Object getHandOverByOwner(Map<String,Object> map);

}
