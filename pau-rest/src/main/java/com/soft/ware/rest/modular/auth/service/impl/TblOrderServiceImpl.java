package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOrderMapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class TblOrderServiceImpl extends ServiceImpl<TblOrderMapper,TblOrder> implements TblOrderService {

    @Resource
    private TblOrderMapper orderMapper;

    @Override
    public TblOrder createOrder(TblOwnerStaff user, AddOrderParam param) {
        Date date = new Date();
        TblOrder o = new TblOrder();
        o.setOwner(user.getOwner());

        o.setNo(param.getNo());
        o.setMoneyChannel(param.getMoney_channel());
        o.setMoney(param.getMoney());
        o.setSource(param.getSource());
        o.setMoney(param.getMoney());
        o.setPayMoney(param.getMoney_shishou());//todo 不对应
        o.setPayAt(param.getPay_at());//todo 不对应
        o.setCreatedAt(date);
        o.setRemark("收银机订单");
        //o.setCreatedBy(user.getId());//todo 有疑问

        param.getMoney_dpay();//todo 优惠后金额
        param.getMoney_zhaol();//todo 找不到

        o.setStatus(param.getStatus());
        o.setSettlementBy(param.getSettlement_by());
        o.setGoods(param.getGoods());
        Integer insert = orderMapper.insert(o);
        return (insert != null && insert > 0 ) ? o : null;
    }
}