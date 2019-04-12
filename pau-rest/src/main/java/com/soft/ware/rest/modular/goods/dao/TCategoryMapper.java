package com.soft.ware.rest.modular.goods.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.goods.model.TCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface TCategoryMapper extends BaseMapper<TCategory> {

    List<Map<String, Object>> findMaps(@Param("map") Map<String, Object> map);

    List<TCategory> selectParentCategoryList(Map<String,Object> map);

    List<TCategory> selectChildrenCategoryList(Map<String,Object> map);

    boolean updateOrSort(CategorySortParam categorySortParam);

}
