package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblGoodsMapper extends BaseMapper<TblGoods> {

    List<Map> findPage(@Param("owner") String owner, @Param("param") GoodsPageParam param, @Param("page") Page page);


    Long findPageCount(@Param("owner") String owner, @Param("param") GoodsPageParam param);
}
