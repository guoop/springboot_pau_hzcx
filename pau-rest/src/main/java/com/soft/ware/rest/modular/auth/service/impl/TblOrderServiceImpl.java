package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblOrderMapper;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.auth.controller.dto.AddOrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderDeleteParam;
import com.soft.ware.rest.modular.auth.controller.dto.OrderParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblOrderService;
import com.soft.ware.rest.modular.auth.util.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TblOrderServiceImpl extends ServiceImpl<TblOrderMapper,TblOrder> implements TblOrderService {

    @Resource
    private TblOrderMapper orderMapper;

    /**
     * 收银app下单
     * @param user
     * @param param
     * @return
     */
    @Override
    public TblOrder createOrder(SessionUser user, AddOrderParam param) {
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

    /**
     * 收银app分页查询订单列表
     * @param user
     * @param page
     * @param param
     * @return
     */
    @Override
    public List<Map> findPage(SessionUser user, Page page,OrderParam param,Integer source) {
        Long count = orderMapper.findListCount(user, param,source);
        page.setTotal(count);
        List<Map> list = orderMapper.findList(user, page, param,source);
        return list;
    }

    /**
     *
     * 收银app根据订单号查询订单详情
     * @param user
     * @param no
     * @return
     */
    @Override
    public TblOrder findByNo(SessionUser user,String no) {
        return orderMapper.findByNo(user,no);
    }


    @Override
    public boolean updateStatus(SessionUser user, String orderNO, String status) {
        int i = orderMapper.updateStatusByNo(user, orderNO, status);
        if (i != 1) {
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
        }
        return true;
    }

    @Override
    public boolean customerDelete(SessionUser user, OrderDeleteParam param) {
        int i = orderMapper.customerDelete(user, param);
        if (i == 1) {
            return true;
        } else {
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
        }
    }

    @Override
    public boolean customerCancel(SessionUser user, OrderDeleteParam param) {
        int i = orderMapper.customerCancel(user, param);
        if (i != 1) {
            throw new PauException(BizExceptionEnum.UPDATE_ERROR);
        }
        return true;
    }

}