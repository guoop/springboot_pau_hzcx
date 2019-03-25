package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblGoodsMapper;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblGoodsService;
import com.soft.ware.rest.modular.auth.util.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TblGoodsServiceImpl extends ServiceImpl<TblGoodsMapper,TblGoods> implements TblGoodsService {

    @Resource
    private TblGoodsMapper tblGoodsMapper;

    @Override
    public List<Map> findPage(SessionUser user, Page page, GoodsPageParam param) {
        Long count = tblGoodsMapper.findPageCount(user.getOwner(), param);
        page.setTotal(count);
        return tblGoodsMapper.findPage(user.getOwner(), param, page);
    }

    @Override
    public TblGoods findById(Long id) {
        return tblGoodsMapper.selectOne(new TblGoods().setId(id));

    }

    @Override
    public List<TblGoods> findAll(SessionUser user,List<String> ids) {
        return tblGoodsMapper.findListByIds(user, ids);
    }
}