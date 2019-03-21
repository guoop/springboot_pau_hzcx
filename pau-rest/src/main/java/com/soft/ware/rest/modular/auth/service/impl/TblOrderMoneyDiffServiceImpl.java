package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblOrderMoneyDiffMapper;
import com.soft.ware.rest.common.persistence.model.TblOrderMoneyDiff;
import com.soft.ware.rest.modular.auth.service.TblOrderMoneyDiffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOrderMoneyDiffServiceImpl extends ServiceImpl<TblOrderMoneyDiffMapper,TblOrderMoneyDiff> implements TblOrderMoneyDiffService {

    @Resource
    private TblOrderMoneyDiffMapper tblOrderMoneyDiffMapper;

}