package com.soft.ware.rest.common.persistence.dao;

import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblRepository;

public interface TblRepositoryMapper extends BaseMapper<TblRepository> {

    TblRepository getGoodsRepositoryByCode(Map<String,Object> map);
    
}
