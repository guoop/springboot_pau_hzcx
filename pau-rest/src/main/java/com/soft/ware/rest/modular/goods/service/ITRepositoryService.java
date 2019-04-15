package com.soft.ware.rest.modular.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.goods.model.TRepository;

/**
 * <p>
 * 商品库 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITRepositoryService extends IService<TRepository> {

    TRepository selectRepositoryByGoodsCode(String code);

}
