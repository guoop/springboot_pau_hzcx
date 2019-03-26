package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.soft.ware.rest.common.persistence.dao.TblCategoryMapper;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblCategoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TblCategoryServiceImpl extends ServiceImpl<TblCategoryMapper,TblCategory> implements TblCategoryService {

    @Resource
    private TblCategoryMapper tblCategoryMapper;

    @Override
    public List<TblCategory> findAllCategory(SessionUser user) {
        return tblCategoryMapper.finalAll(user.getOwner());
    }

	@Override
	public TblCategory selectByOwner(TblCategory cate) {
		return tblCategoryMapper.selectOne(cate);
	}

}