package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOrderMoneyDiff;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDiffParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwnerUser;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface TblOrderMoneyDiffService extends IService<TblOrderMoneyDiff> {


    /**
     * 根据差额订单号查询
     * @param user
     * @param diffNO
     * @return
     */
    TblOrderMoneyDiff findByNo(SessionOwnerUser user, String diffNO);

    /**
     * 根据订单查询差额订单
     * @param user
     * @param order
     * @return
     */
    TblOrderMoneyDiff findByNo(SessionOwnerUser user, TblOrder order);

    /**
     * 创建差额订单
     * @param user
     * @param param
     */
    boolean create(SessionOwnerUser user, OrderDiffParam param);

    /**
     * 补差价付款
     * @param result 付款结果
     * @param user   付款用户
     * @return
     */
    boolean update(WxPayOrderNotifyResult result, SessionUser user);
}
