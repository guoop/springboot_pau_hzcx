package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.dao.TblGoodsMapper;
import com.soft.ware.rest.common.persistence.model.TblGoods;
import com.soft.ware.rest.common.persistence.model.TblGoodsStorage;
import com.soft.ware.rest.common.persistence.model.TblRepository;
import com.soft.ware.rest.modular.auth.controller.dto.*;
import com.soft.ware.rest.modular.auth.service.ImService;
import com.soft.ware.rest.modular.auth.service.TblGoodsService;
import com.soft.ware.rest.modular.auth.service.TblGoodsStorageService;
import com.soft.ware.rest.modular.auth.service.TblRepositoryService;
import com.soft.ware.rest.modular.auth.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TblGoodsServiceImpl extends BaseService<TblGoodsMapper,TblGoods> implements TblGoodsService {

    @Resource
    private TblGoodsMapper tblGoodsMapper;

    @Autowired
    private TblGoodsStorageService storageService;

    @Autowired
    private TblRepositoryService repositoryService;

    @Autowired
    private ImService imService;

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

	@Override
	public boolean updateGoodsStatus(SessionUser user,GoodsUpdateParam param) {
        TblGoods goods = tblGoodsMapper.selectById(param.getId());
        goods.setStatus(Integer.valueOf(param.getStatus()));
        Integer row = tblGoodsMapper.update(goods, new EntityWrapper<>(new TblGoods().setId(goods.getId()).setOwner(user.getOwner())));
        return row == 1;
    }

    @Override
    public TblGoodsMapper getTblGoodsMapper() {
        return tblGoodsMapper;
    }

    @Override
    public boolean updateStock(SessionOwnerUser user, List<String> ids, List<String> nums){
        return tblGoodsMapper.updateStock(user, ids, nums);
    }

    @Override
    public List<TblGoods> findByCode(SessionOwner user, String code) {
        return tblGoodsMapper.selectList(new EntityWrapper<>(new TblGoods().setOwner(user.getOwner()).setCode(code)));
    }

    @Override
    public boolean addByHand(SessionOwnerUser user, TblGoods g, TblGoodsStorage s) {
        Date date = new Date();
        List<TblGoods> list = this.findByCode(user, g.getCode());
        if (!list.isEmpty()) {
            throw new PauException(BizExceptionEnum.GOODS_REPEAT);
        }
        g.setCreatedAt(date);
        g.setCreatedBy(Long.valueOf(user.getId()));
        g.setOwner(user.getOwner());
        g.setStatus(TblGoods.status_1);
        g.setSortNum(1);
        g.setSource(TblGoods.source_2);
        g.setIsDelete(TblGoods.is_delete_0);
        if(this.insert(g)){
            imService.sendAddGoodsNotify(user, g);
            s.setOwner(user.getOwner());
            s.setGoodsId(g.getId());
            s.setBeforeBaseline(BigDecimal.ZERO);
            s.setCreatedAt(date);
            s.setInAt(date);
            return storageService.insert(s);
        }


        return  false;
    }

    @Override
    public boolean addByScan(SessionOwnerUser user, TblGoods g, TblGoodsStorage s) {
        Date date = new Date();
        List<TblGoods> list = this.findByCode(user, g.getCode());
        if (!list.isEmpty()) {
            throw new PauException(BizExceptionEnum.GOODS_REPEAT);
        }
        g.setCreatedAt(date);
        g.setCreatedBy(Long.valueOf(user.getId()));
        g.setOwner(user.getOwner());
        g.setStatus(TblGoods.status_1);
        g.setSortNum(1);
        g.setSource(TblGoods.source_1);
        g.setIsDelete(TblGoods.is_delete_0);
        if(this.insert(g)){
            imService.sendAddGoodsNotify(user, g);
            s.setOwner(user.getOwner());
            s.setGoodsId(g.getId());
            s.setBeforeBaseline(BigDecimal.ZERO);
            s.setCreatedAt(date);
            s.setInAt(date);

            if (repositoryService.findByCode(g.getCode()).isEmpty()) {
                TblRepository r = new TblRepository();
                r.setCode(g.getCode());
                r.setCreatedAt(date);
                r.setMeasurement(g.getMeasurementUnit());
                r.setName(g.getName());
                r.setPics(g.getPics());
                r.setIsDelete(TblRepository.is_delete_0);
                repositoryService.insert(r);
            }



            return storageService.insert(s);
        }


        return false;
    }

    private void notifyOwnerAddGoods(SessionOwnerUser user,TblGoods goods){
        imService.sendAddGoodsNotify(user, goods);
    }

    public void setTblGoodsMapper(TblGoodsMapper tblGoodsMapper) {
        this.tblGoodsMapper = tblGoodsMapper;
    }
}