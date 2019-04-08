package com.soft.ware.rest.modular.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwner;
import com.soft.ware.rest.modular.goods.model.TCategory;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITCategoryService extends IService<TCategory> {

    List<TCategory> findAllCategory(SessionOwner user);
}
