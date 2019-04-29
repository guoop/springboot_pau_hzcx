package com.soft.ware.rest.modular.handover.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.handover.dao.THandoverRecordMapper;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;
import com.soft.ware.rest.modular.handover.service.ITHandoverRecordService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.ITOwnerStaffService;
import org.apache.commons.lang.time.DateUtils;
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
    private ITOwnerStaffService staffService;

    @Override
    public List<Map<String, Object>> findMaps(Map<String, Object> map) {
        return mapper.findMaps(map);
    }

    @Override
    public Kv<String, Object> findMap(Map<String, Object> map) {
        List<Map<String, Object>> maps = findMaps(map);
        return maps.size() == 1 ? Kv.toKv(maps.get(0)) : null;
    }

    @Override
    public THandoverRecord findOne(Map<String,Object> map) throws Exception {
        return BeanMapUtils.toObject(map, THandoverRecord.class);
    }


    @Override
    public List<Map<String,Object>> findPage(SessionUser user, HandoverPageParam param, Page page) {
        Kv<String, Object> params = Kv.obj("ownerId", user.getOwnerId()).set("staffId", user.getId());
        TOwnerStaff staff = staffService.findByLoginName(user.getPhone());
        //普通用户只能查看自己的交接班记录,店主可以查看所有的
        if (!TOwnerStaff.shopkeeperFlag.equals(staff.getFunctionList())) {
            params.set("staffId", user.getId());
        }
        if (param.getStart() != null) {
            params.set("start", param.getStart());
            params.set("end", DateUtils.addDays(param.getStart(), 1));
        }
        if (param.getEnd() != null) {
            params.set("end", DateUtils.addDays(param.getEnd(), 1));
        }
        long count = (Long) findMap(params.clone().set("pageCount", "true")).get("count");
        params.set("page", page.setTotal(count));
        return mapper.findMaps(params);
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
