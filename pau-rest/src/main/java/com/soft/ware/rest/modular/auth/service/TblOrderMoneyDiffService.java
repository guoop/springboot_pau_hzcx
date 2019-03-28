package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.soft.ware.rest.common.persistence.model.TblOrderMoneyDiff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

public interface TblOrderMoneyDiffService extends IService<TblOrderMoneyDiff> {

    TblOrderMoneyDiff findByNo(SessionUser user, String diffNO);

    /**
     * 补差价付款
     * @param result 付款结果
     * @param user   付款用户
     * @return
     */
    boolean update(WxPayOrderNotifyResult result, SessionUser user);
}
