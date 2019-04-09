package com.soft.ware.rest.modular.order.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.order.dao.TOrderMapper;
import com.soft.ware.rest.modular.order.model.TOrder;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 线上订单信息 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements ITOrderService {

    @Resource
    private TOrderMapper orderMapper;


    @Override
    public List<TOrder> selectOrderListByMap(Map<String, Object> param) {
        return orderMapper.selectOrderListByMap(param);
    }
}
