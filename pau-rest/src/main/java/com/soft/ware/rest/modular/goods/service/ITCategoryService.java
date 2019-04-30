package com.soft.ware.rest.modular.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.util.Kv;
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

    Kv<String, Object> findMap(Map<String, Object> map);

    /**
     * 根据id查询子分类
     * @param user
     * @param category    父级分类，这个字段也可能不是一个父级分类
     * @param returnDefault 如果这个分类不是一个父级分类，是否返回自身作为默认值
     * @return
     */
    List<Map<String,Object>> findChild(SessionUser user,TCategory category, boolean returnDefault) throws Exception;

    List<TCategory> selectParentCategoryList(Map<String,Object> map);

    List<TCategory> selectChildrenCategoryList(Map<String,Object> map);

    boolean updateOrSort(CategorySortParam categorySortParam);

    List<TCategory> selectCategoryByIds(String[] ids);

    /**
     * 修改或保存分类列表
     * @param user
     * @param category
     * @return
     */
    boolean saveOrUpdate(SessionUser user, TCategory category);
}
