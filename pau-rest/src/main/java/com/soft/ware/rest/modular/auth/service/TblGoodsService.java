package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.dao.TblGoodsMapper;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsUpdateParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;
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
    boolean updateStock(SessionOwnerUser user, List<String> ids, List<String> nums);
}
