package com.soft.ware.rest.modular.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblOrderMoneyDiffMapper;
import com.soft.ware.rest.common.persistence.model.TblOrderMoneyDiff;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblOrderMoneyDiffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class TblOrderMoneyDiffServiceImpl extends BaseService<TblOrderMoneyDiffMapper,TblOrderMoneyDiff> implements TblOrderMoneyDiffService {

    @Resource
    private TblOrderMoneyDiffMapper mapper;

    @Override
    public TblOrderMoneyDiff findByNo(SessionUser user, String diffNO) {
        return mapper.selectOne(new TblOrderMoneyDiff().setNo(diffNO).setOwner(user.getOwner()));
    }

    @Override
    public boolean update(WxPayOrderNotifyResult result, SessionUser user) {
        TblOrderMoneyDiff order = findByNo(user, result.getOutTradeNo());
        order.setStatus(TblOrderMoneyDiff.refund_status_1);
        order.setPayAt(new Date());
        order.setPayResponse(JSON.toJSONString(result));
        Integer update = mapper.update(order, new EntityWrapper<>(new TblOrderMoneyDiff().setNo(order.getNo())));
        if (update != 1) {
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        return true;
    }
}