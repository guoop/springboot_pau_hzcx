package com.soft.ware.rest.modular.order.service;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.baomidou.mybatisplus.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.order.controller.dto.CreateOrderParam;
import com.soft.ware.rest.modular.order.model.TOrder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 线上订单信息 服务类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
public interface ITOrderService extends IService<TOrder> {


    List<Map<String,Object>> findPage(SessionUser user, Page page, OrderPageParam param, Integer... source);

    long findPageCount(SessionUser user, OrderPageParam param, Integer... sources);

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

    List<HashMap<String,Object>> selectOrdersListByMap(Map<String,Object> map , Page page);

    /**
     * 部分退款/全额退款（取消订单）
     * @param map
     * @param sessionUser
     * @return
     * @throws WxPayException
     */
    boolean orderRefund(Map<String,Object> map , SessionUser sessionUser) throws WxPayException;

    WxMaTemplateMessage buildOrderTemplateMessage(SessionUser user,String templateKey, String fromID, TOrder order,List<String> goodsNames, TAddress address) throws Exception;

    TOrder createMiniAppOrder(SessionUser user, CreateOrderParam param) throws Exception;

    boolean updatePayCallback(WxPayOrderNotifyResult result, SessionUser user,TOrder order) throws Exception;


    /**
     * 买家删除订单
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

    int selectOrderCount(String ownerId);

    Map<String,Object> selectOrderDetailById(String orderNo);

    /**
     * 支付时设置,收货人地址电话信息
     * @param order
     * @return
     */
    boolean updateByVersion(TOrder order);

    boolean orderSignStatu(SessionUser sessionUser,Map<String,Object> param);

    /**
     * 下单支付接口
     * @param user
     * @param no
     * @param source
     * @param spbill_create_ip
     * @param phone
     * @param remark
     * @return
     * @throws Exception
     */
    WxPayMpOrderResult unifiedorder(SessionUser user, String no, Integer source, String spbill_create_ip, String phone, String remark, Date pickupTime) throws Exception;

    /**
     * 差价支付
     * @param user
     * @param diffNo
     * @param spbill_create_ip
     * @return
     * @throws Exception
     */
    WxPayMpOrderResult unifiedorderDiff(SessionUser user,String diffNo, String spbill_create_ip) throws Exception;

    /**
     * 查下下单/或退款
     * @param param
     * @param sessionUser
     * @return
     * @throws Exception
     */
    boolean diffMoney(Map<String,Object> param,SessionUser sessionUser) throws Exception;

}
