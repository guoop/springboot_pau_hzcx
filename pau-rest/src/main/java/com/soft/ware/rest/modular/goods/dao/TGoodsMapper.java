package com.soft.ware.rest.modular.goods.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.model.TGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
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

    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> map);

    List<Map<String,Object>> selectTGoodsListByMap(@Param("params") Map<String, Object> param,@Param("page") Page page);

    long selectTGoodsListByMapCount(@Param("params") Map<String,Object> params);

    boolean updateGoodsTopTimeOrStatus(Map<String,Object> map);

    Map<String,Object> findById(@Param("id") String id);

    @Update(value = "update t_goods g set g.top_time = #{date} where g.id = #{id}")
    boolean top(@Param("id") String id, @Param("date") Date date);

    long findMapsCount(@Param("params") Map<String,Object> params);

    List<Map<String,Object>> findByCode(@Param("ownerId") String ownerId, @Param("code") String code);
}
