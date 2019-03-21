package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblAppVersion;
import org.apache.ibatis.annotations.Param;

public interface TblAppVersionMapper extends BaseMapper<TblAppVersion> {

    TblAppVersion findLast(@Param("platformCode") String platformCode);
}
