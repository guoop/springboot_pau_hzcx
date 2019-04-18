package com.soft.ware.rest.modular.order.service;

import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import com.baomidou.mybatisplus.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.auth.controller.dto.DiffParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.order.controller.dto.CreateOrderParam;
import com.soft.ware.rest.modular.order.model.TOrder;

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


    List<TOrder> selectOrderListByMap(Map<String,Object> param);

    List<Map<String,Object>> findPage(SessionUser user, Page page, OrderPageParam param, Integer... source);

    long findPageCount(SessionUser user, OrderPageParam param, Integer... sources);

    List<Map<String,Object>> findMaps(Map<String,Object> map);

    Map<String,Object> findMap(Map<String,Object> map);

    List<HashMap<String,Object>> selectOrdersListByMap(Map<String,Object> map);

    boolean orderRefund(Map<String,Object> map , SessionUser sessionUser);


    WxMaTemplateMessage buildOrderTemplateMessage(String templateID, String fromID, TOrder order,List<String> goodsNames, TAddress address);

    TOrder createMiniAppOrder(SessionUser user, CreateOrderParam param) throws Exception;

    boolean updatePayCallback(WxPayOrderNotifyResult result, SessionUser user,TOrder order) throws Exception;

    boolean customerDelete(SessionUser user, OrderDeleteParam param);

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
    WxPayMpOrderResult unifiedorder(SessionUser user, String no, Integer source, String spbill_create_ip, String phone, String remark) throws Exception;

    WxPayMpOrderResult unifiedorderDiff(SessionUser user, DiffParam param, String spbill_create_ip) throws Exception;

    boolean diffMoney(Map<String,Object> param,SessionUser sessionUser);

}
