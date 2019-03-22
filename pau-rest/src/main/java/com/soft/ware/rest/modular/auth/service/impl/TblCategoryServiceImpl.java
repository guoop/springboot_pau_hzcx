package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblCategoryMapper;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.controller.dto.Customer;
import com.soft.ware.rest.modular.auth.service.TblCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TblCategoryServiceImpl extends ServiceImpl<TblCategoryMapper,TblCategory> implements TblCategoryService {

    @Resource
    private TblCategoryMapper tblCategoryMapper;

    @Override
    public List<TblCategory> findAllCategory(TblOwnerStaff staff) {
        return tblCategoryMapper.finalAll(staff.getOwner());
    }

    @Override
    public List<TblCategory> findAllCategory(Customer customer) {
        return tblCategoryMapper.finalAll(customer.getOwner());
    }
}