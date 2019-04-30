package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.goods.dao.TUnitMapper;
import com.soft.ware.rest.modular.goods.model.TUnit;
import com.soft.ware.rest.modular.goods.service.ITUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 单位表 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TUnitServiceImpl extends ServiceImpl<TUnitMapper, TUnit> implements ITUnitService {

    @Resource
    private TUnitMapper mapper;
}
