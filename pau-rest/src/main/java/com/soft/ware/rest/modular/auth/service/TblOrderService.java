package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.util.Page;

import java.util.List;
import java.util.Map;

public interface TblOrderService extends IService<TblOrder> {

    /**
     * 收银机添加订单
     * @param user
     * @param param
     * @return
     */
    TblOrder createOrder(SessionUser user, AddOrderParam param);

    /**
     * 收银机订单列表
     * @param user
     * @param page
     * @param param
     * @param source
     * @return
     */
    List<Map> findPage(SessionUser user, Page page, OrderParam param,Integer... source);

    /**
     * 根据订单号查询订单详情
     * @param no
     * @return
     */
    TblOrder findByNo(SessionUser user, String no);

    /**
     * 更新订单状态
     * @param user
     * @param orderNO
     * @param status
     * @return
     */
    boolean updateStatus(SessionUser user, String orderNO, String status);


    /**
     * 买家用户删除订单
     * @param user
     * @param param
     * @return
     */
    boolean customerDelete(SessionUser user, OrderDeleteParam param);

    /**
     * 买家取消订单
     * @param user
     * @param param
     * @return
     */
    boolean customerCancel(SessionUser user, OrderDeleteParam param);
    /**
     * 通过订单状态查询订单列表
     * @param map
     * @return
     */
    List<TblOrder> findOrderListByStatus(Map<String,Object> map);


    /**
     * 微信支付回调结果处理
     * @param result
     * @return
     */
    boolean update(WxPayOrderNotifyResult result,SessionUser user,String no) throws Exception;


    /**
     * 微信支付小程序下单
     * @param user
     * @param param
     * @return
     */
    TblOrder createMiniAppOrder(SessionUser user, CartParam param);
}
