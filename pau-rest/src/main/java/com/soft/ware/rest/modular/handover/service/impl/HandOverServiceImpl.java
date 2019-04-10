package com.soft.ware.rest.modular.handover.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.handover.dao.HandOverMapper;
import com.soft.ware.rest.common.persistence.dao.TblOwnerStaffMapper;
import com.soft.ware.rest.modular.handover.model.HandOver;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.handover.service.IHandOverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HandOverServiceImpl extends ServiceImpl<HandOverMapper, HandOver> implements IHandOverService {

    @Resource
    private HandOverMapper overMapper;

    @Resource
    private TblOwnerStaffMapper staffMapper;


    @Override
    public HandOver over(SessionUser user, HandoverParam param) {
        //todo yancc 考虑改成session中
        TblOwnerStaff staff = staffMapper.selectById(user.getId());
        HandOver o = new HandOver();
        Date date = new Date();
        o.setOwner(user.getOwnerId());
        o.setUserPhone(user.getPhone());
        o.setUserName(staff.getName());

        o.setPospalcode(param.getPospalcode());
        o.setSyncAt(date);
        o.setOptionStart(param.getOptionstart());
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

	@SuppressWarnings("unchecked")
	@Override
	public List<HandOver> getHandOver(Map<String,Object> param) {
		List<HandOver> list = null;

        overMapper.getHandOverList(param);

		return list;
	}
}