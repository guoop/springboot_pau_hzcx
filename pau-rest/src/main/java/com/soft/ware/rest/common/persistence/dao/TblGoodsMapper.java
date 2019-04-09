package com.soft.ware.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TblGoodsMapper extends BaseMapper<TblGoods> {

    List<Map> findPage(@Param("owner") String owner, @Param("param") GoodsPageParam param, @Param("page") Page page);


    Long findPageCount(@Param("owner") String owner, @Param("param") GoodsPageParam param);

    List<TblGoods> findListByIds(@Param("user") SessionUser user, @Param("ids") List<String> ids);

    /**
     * 更新库存数量
     * @param user
     * @param ids
     * @param nums
     * @return
     */
    boolean updateStock(@Param("user") SessionUser user, @Param("ids") List<String> ids, @Param("nums") List<String> nums);
}
