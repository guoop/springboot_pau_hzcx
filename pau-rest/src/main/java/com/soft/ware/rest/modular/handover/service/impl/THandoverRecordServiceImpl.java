package com.soft.ware.rest.modular.handover.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.handover.dao.THandoverRecordMapper;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;
import com.soft.ware.rest.modular.handover.service.ITHandoverRecordService;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交接班记录表 服务实现类
 * </p>
 * @author paulo123
 * @since 2019-04-10
 */
@Service
public class THandoverRecordServiceImpl extends ServiceImpl<THandoverRecordMapper, THandoverRecord> implements ITHandoverRecordService {

    @Resource
    private THandoverRecordMapper mapper;

    @Resource
    private TOwnerStaffService staffService;

    @Override
    public List<THandoverRecord> getHandOver(Map<String, Object> param) {
        return mapper.getHandOver(param);
    }


    @Override
    public THandoverRecord over(SessionUser user, HandoverParam param) {
        //todo yancc 考虑改成session中
        //TOwnerStaff staff = staffService.selectById(user.getId());
        THandoverRecord o = new THandoverRecord();
        Date date = new Date();
        o.setOwnerId(user.getOwnerId());
        o.setStaffId(user.getId());
        o.setDeviceId(param.getPospalcode());//不一致
        o.setSyncTime(date);
        o.setStartTime(new Date(param.getOptionstart()));
        o.setEndTime(new Date(param.getOptionat()));
        //o.setFisvoidTime(param.getFisvoidtime());
        //o.setLasvoidTime(param.getLasvoidtime());
        o.setOrderNum(param.getOrdernum());
        o.setOrderRefundNum(param.getOrdertuinum());
        o.setOrderReturnNum(param.getOrderreturnnum());
        o.setAllMoney(param.getAllmoney());
        o.setOrderMoney(param.getOrdermoney());
        o.setAllMoney(param.getMoney_shishou());
        o.setOddChangeMoney(param.getZhaoling());
        o.setMemberRecharge(param.getMembercz());
        o.setOrderRefundMoney(param.getOrdertuimoney());
        o.setOrderReturnMoney(param.getOrderreturnmoney());
        o.setCreateTime(date);
        o.setCreater(user.getPhone());
        //o.setWxpay(param.getWxpay());
        //o.setWxpayNum(param.getWxpaynum());
        //o.setAlipay(param.getAlipay());
        //o.setAlipayNum(param.getAlipaynum());
        //o.setUnionPay(param.getUnionpay());
        //o.setUnionPayNum(param.getUnionpaynum());
        //o.setMoneyPay(param.getMoneypay());
        //o.setMoneyPayNum(param.getMoneypaynum());
        //o.setMoneyMemberPay(param.getMoneymemberpay());
        //o.setMemberPayNum(param.getMomberpaynum());
        return insert(o) ? o : null;
    }
}
