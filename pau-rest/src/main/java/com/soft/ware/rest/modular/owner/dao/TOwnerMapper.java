package com.soft.ware.rest.modular.owner.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.owner.model.TOwner;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商户信息 Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface TOwnerMapper extends BaseMapper<TOwner> {

    List<Map<String, Object>> findMap(@Param("map") Map<String, Object> map);

    Map<String,Object> selectOwnerInfoByOwnerId(String OwnerId);
}
