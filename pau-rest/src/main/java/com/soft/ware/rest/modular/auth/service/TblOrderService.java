package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.util.Page;
import me.chanjar.weixin.common.error.WxErrorException;

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
    List<Map> findPage(SessionUser user, Page page, OrderPageParam param,Integer... source);

    /**
     * 根据订单号查询订单详情
     * @param no
     * @return
     */
    TblOrder findByNo(SessionUser user, String no);


    /**
     * 根据订单号查询订单详情
     * @param user
     * @param no
     * @return
     */
    TblOrder findByNo(SessionOwnerUser user, String no);

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

    /**
     * 当用户查询订单时，例如支付成功，但是订单状态没有发生改变时，根据微信支付状态，修改订单状态
     * @param result
     * @param user
     * @return
     */
    boolean update(WxPayOrderQueryResult result, SessionUser user);

    /**
     * 商家小程序分页查询订单
     * @param user
     * @param page
     * @param param
     * @return
     */
    List<Map> findOwnerOrderPage(SessionUser user, Page page, OrderPageParam param);

    /**
     * 商家用户查询订单详情
     * @param user
     * @param no
     * @return
     */
    Map findOwnerOrder(SessionUser user, String no);

    /**
     * 商家更新订单状态
     * @param user
     * @param status
     * @param order
     * @param owner
     * @param reason 原因
     * @return
     */
    boolean updateOwnerOrder(SessionOwnerUser user, String status, TblOrder order, TblOwner owner,String reason) throws WxErrorException;

    /**
     * 商家退款
     * @param user
     * @param param
     * @return
     */
    boolean refund(SessionOwnerUser user, OrderRefundParam param) throws WxPayException;
}
