package com.soft.ware.rest.modular.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.goods.model.TRepository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITRepositoryService extends IService<TRepository> {

    List<Map<String,Object>> findMaps(Map<String,Object> params);

    Kv<String,Object> findMap(Map<String,Object> params);

    TRepository findOne(Map<String, Object> params) throws Exception;

    List<TRepository> findList(Kv<String, Object> params) throws Exception;

    TRepository selectRepositoryByGoodsCode(String code);

}
