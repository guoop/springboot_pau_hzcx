package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.dao.TblGoodsMapper;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblGoodsStorage;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsUpdateParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;

import java.util.List;
import java.util.Map;

public interface TblGoodsService extends IService<TblGoods> {


    List<Map> findPage(SessionUser user, Page page, GoodsPageParam param);

    TblGoods findById(Long id);

    /**
     * 根据商品id in 查询所有商品
     * @param ids 商品id
     * @return
     */
    List<TblGoods> findAll(SessionUser user,List<String> ids);

    /**
     * 更新商品状态
     * @param param
     * @param user
     * @return
     */
    boolean updateGoodsStatus(SessionUser user,GoodsUpdateParam param);

    TblGoodsMapper getTblGoodsMapper();

    /**
     * 更新库存信息
     * @param user
     * @param ids 商品id
     * @param nums 商品数量
     * @return
     */
    boolean updateStock(SessionUser user, List<String> ids, List<String> nums);

    /**
     * 查询商品详情，根据商品条码
     * @param code
     * @return
     */
    List<TblGoods> findByCode(SessionUser user, String code);

    /**
     * 手工添加商品
     * @param user
     * @param g
     * @param s
     * @return
     */
    boolean addByHand(SessionUser user, TblGoods g, TblGoodsStorage s);

    /**
     * 扫码添加商品
     * @param user
     * @param g
     * @param s
     * @return
     */
    boolean addByScan(SessionUser user, TblGoods g,TblGoodsStorage s);
}
