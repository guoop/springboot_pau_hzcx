package com.soft.ware.rest.modular.goods.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.modular.goods.dao.TRepositoryMapper;
import com.soft.ware.rest.modular.goods.model.TRepository;
import com.soft.ware.rest.modular.goods.service.ITRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 商品库 服务实现类
 * </p>
 *
 * @author paulo123
 * @since 2019-04-08
 */
@Service
public class TRepositoryServiceImpl extends ServiceImpl<TRepositoryMapper, TRepository> implements ITRepositoryService {

    @Resource
    private TRepositoryMapper tRepositoryMapper;

    @Override
    public TRepository selectRepositoryByGoodsCode(String code) {
        return tRepositoryMapper.selectRepositoryByGoodsCode(code);
    }

}
