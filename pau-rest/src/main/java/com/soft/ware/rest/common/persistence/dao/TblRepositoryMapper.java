package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblRepository;

import java.util.Map;

public interface TblRepositoryMapper extends BaseMapper<TblRepository> {

    TblRepository getGoodsRepositoryByCode(Map<String,Object> map);
    
}
