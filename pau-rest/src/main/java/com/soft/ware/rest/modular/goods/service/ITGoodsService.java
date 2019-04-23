package com.soft.ware.rest.modular.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.model.TGoods;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITGoodsService extends IService<TGoods> {

    List<Map<String, Object>> findPage(SessionUser user, Page page, GoodsPageParam param);

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

    List<Map<String,Object>>  selectTGoodsListByMap(Map<String,Object> map, Page page) throws ParseException;

    boolean updateGoodsTopTimeOrStatus(Map<String,Object> map);

    HashMap<String,Object> findById(String id);
}
