package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblGoodsMapper;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
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
    public List<Map> findPage(TblOwnerStaff staff, Page page) {
        Long count = tblGoodsMapper.findPageCount(staff);
        page.setTotal(count);
        return tblGoodsMapper.findPage(staff, page);
    }
}