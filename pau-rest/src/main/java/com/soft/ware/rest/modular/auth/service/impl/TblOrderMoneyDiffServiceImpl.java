package com.soft.ware.rest.modular.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblOrderMoneyDiffMapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOrderMoneyDiff;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDiffParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblOrderMoneyDiffService;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.util.WXContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Transactional
public class TblOrderMoneyDiffServiceImpl extends BaseService<TblOrderMoneyDiffMapper,TblOrderMoneyDiff> implements TblOrderMoneyDiffService {

    @Resource
    private TblOrderMoneyDiffMapper mapper;

    @Autowired
    private TblOrderService orderService;

    @Override
    public TblOrderMoneyDiff findByNo(SessionUser user, String diffNO) {
        return mapper.selectOne(new TblOrderMoneyDiff().setNo(diffNO).setOwner(user.getOwnerId()));
    }

    @Override
    public TblOrderMoneyDiff findByNo(SessionUser user, TblOrder order) {
        return mapper.selectOne(new TblOrderMoneyDiff().setOrderNo(order.getNo()).setOwner(user.getOwnerId()));
    }

    @Override
    public boolean create(SessionUser user, OrderDiffParam param) {
        Date date = new Date();
        TblOrder order = orderService.findByNo(user, param.getOrderNO());
        Integer status = order.getStatus();
        if (!TblOrder.STATUS_2.equals(status) && !TblOrder.STATUS_3.equals(status) && TblOrder.STATUS_10.equals(status)) {
            throw new PauException(BizExceptionEnum.ORDER_STATUS_NOT_SUPPORT);
        }
        TblOrderMoneyDiff diff = this.findByNo(user, order);

        BigDecimal moneyDiff = param.getMoney().min(order.getMoney()).setScale(2, WXContants.big_decimal_sale);
        if (diff == null) {
            diff = new TblOrderMoneyDiff();
            diff.setNo(IdGenerator.getId());
            diff.setOwner(user.getOwnerId());
            diff.setOrderNo(order.getNo());
            diff.setMoney(param.getMoney());

            diff.setMoneyDiff(moneyDiff);
            diff.setPic(param.getPic());
            diff.setCreatedAt(date);
            diff.setStatus(TblOrderMoneyDiff.status_0);
            return this.insert(diff);
        } else {
            diff.setMoney(param.getMoney());
            diff.setMoneyDiff(moneyDiff);
            diff.setPic(param.getPic());
            return this.update(diff, new EntityWrapper<>(new TblOrderMoneyDiff().setId(diff.getId()).setOwner(user.getOwnerId())));
        }
    }

    @Override
    public boolean update(WxPayOrderNotifyResult result, SessionUser user) {
        TblOrderMoneyDiff order = findByNo(user, result.getOutTradeNo());
        order.setStatus(TblOrderMoneyDiff.refund_status_1);
        order.setPayAt(DateUtil.parse(result.getTimeEnd(),"yyyyMMddHHmmss"));
        order.setPayResponse(JSON.toJSONString(result));
        Integer update = mapper.update(order, new EntityWrapper<>(new TblOrderMoneyDiff().setId(order.getId())));
        if (update != 1) {
            throw new PauException(BizExceptionEnum.ORDER_CREATE_FAIL);
        }
        return true;
    }
}