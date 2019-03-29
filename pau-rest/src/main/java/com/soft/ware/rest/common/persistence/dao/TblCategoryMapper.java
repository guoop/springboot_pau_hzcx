package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblCategoryMapper extends BaseMapper<TblCategory> {

    List<TblCategory> finalAll(@Param(value = "owner")String owner);

    boolean updateSort(@Param("param") CategorySortParam param);
}
