package com.soft.ware.rest.modular.handover.service.impl;

import com.soft.ware.core.util.DateUtil;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.HandOverMapper;
import com.soft.ware.rest.common.persistence.model.HandOver;
import com.soft.ware.rest.modular.handover.service.IHandOverService;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class HandOverServiceImpl extends ServiceImpl<HandOverMapper, HandOver> implements IHandOverService {

    @Resource
    private HandOverMapper overMapper;


    @Override
    public HandOver over(TblOwnerStaff user, HandoverParam param) {
        HandOver o = new HandOver();
        Date date = new Date();
        o.setOwner(user.getOwner());
        o.setUserPhone(user.getPhone());
        o.setUserName(user.getName());

        o.setPospalcode(param.getPospalcode());
        o.setSyncAt(date);
        o.setOptionStart(DateUtil.parseTime(param.getOptionstart()));
        o.setOptionaAt(param.getOptionat());
        o.setFistOrderTime(param.getFistordertime());
        o.setLastOrderTime(param.getLastordertime());
        o.setOrderNum(param.getOrdernum());
        o.setOrdertuinum(param.getOrdertuinum());
        o.setOrderReturnNum(param.getOrderreturnnum());
        o.setAllMoney(param.getAllmoney());
        o.setOrderMoney(param.getOrdermoney());
        o.setMoneyShishou(param.getMoney_shishou());
        o.setZhaoLing(param.getZhaoling());
        o.setMembercz(param.getMembercz());
        o.setOrderTuimoney(param.getOrdertuimoney());
        o.setOrderReturnMoney(param.getOrderreturnmoney());
        o.setWxpay(param.getWxpay());
        o.setWxpayNum(param.getWxpaynum());
        o.setAlipay(param.getAlipay());
        o.setAlipayNum(param.getAlipaynum());
        o.setUnionPay(param.getUnionpay());
        o.setUnionPayNum(param.getUnionpaynum());
        o.setMoneyPay(param.getMoneypay());
        o.setMoneyPayNum(param.getMoneypaynum());
        o.setMoneyMemberPay(param.getMoneymemberpay());
        o.setMemberPayNum(param.getMomberpaynum());
        Integer insert = overMapper.insert(o);
        return insert == 1 ? o : null;



    }
}