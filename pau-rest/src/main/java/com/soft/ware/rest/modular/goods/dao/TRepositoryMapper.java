package com.soft.ware.rest.modular.goods.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.goods.model.TRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库 Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface TRepositoryMapper extends BaseMapper<TRepository> {
    List<Map<String, Object>> findMaps(@Param("params") Map<String, Object> params);

    TRepository selectRepositoryByGoodsCode(String code);

}
