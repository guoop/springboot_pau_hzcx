package com.soft.ware.rest.modular.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.goods.model.TCategory;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITCategoryService extends IService<TCategory> {

    List<TCategory> findAllCategory(SessionUser user);

    List<Map<String, Object>> findMaps(Map<String, Object> map);

    List<TCategory> selectParentCategoryList(Map<String,Object> map);

    List<TCategory> selectChildrenCategoryList(Map<String,Object> map);

    boolean updateOrSort(CategorySortParam categorySortParam);
}
