package com.soft.ware.rest.modular.goods.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods_storage.model.TGoodsStorage;

import java.util.Date;
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

    List<Map<String, Object>> findPage(SessionUser user, Page page, GoodsPageParam param) throws Exception;

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Kv<String,Object> findMap(Map<String,Object> map);

    List<Map<String,Object>>  selectTGoodsListByMap(SessionUser user,Map<String,Object> map, Page page) throws Exception;

    boolean updateGoodsTopTimeOrStatus(Map<String,Object> map);

    /**
     * 置顶/取消置顶商品
     * @param goodsId 商品id
     * @param date 置顶时间
     * @return
     */
    boolean top(String goodsId, Date date);

    Map<String,Object> findById(String id);

    boolean addByScan(SessionUser user, TGoods g, TGoodsStorage s) throws Exception;

    /**
     * 根据商品编码查询商品信息
     * @param user
     * @param code
     * @return
     */
    List<Map<String, Object>> findByCode(SessionUser user, String code);

    /**
     * 手动添加商品信息
     * @param user 创建人
     * @param g    商品信息
     * @param s    库存信息
     * @return
     */
    boolean addByManual(SessionUser user, TGoods g, TGoodsStorage s);
}
