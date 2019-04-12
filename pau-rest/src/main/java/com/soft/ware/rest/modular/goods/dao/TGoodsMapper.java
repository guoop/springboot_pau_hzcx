package com.soft.ware.rest.modular.goods.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.model.TGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface TGoodsMapper extends BaseMapper<TGoods> {

    List<Map> findPage(@Param("owner") String owner, @Param("param") GoodsPageParam param, @Param("page") Page page);

    Long findPageCount(@Param("owner") String owner, @Param("param") GoodsPageParam param);

    List<Map<String, Object>> findMaps(@Param("map") Map<String, Object> map);

    List<TGoods> selectTGoodsListByMap(Map<String,Object> map);
}
