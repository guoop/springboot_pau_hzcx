package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblCategoryMapper;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class TblCategoryServiceImpl extends BaseService<TblCategoryMapper,TblCategory> implements TblCategoryService {

    @Resource
    private TblCategoryMapper tblCategoryMapper;

    @Override
    public List<TblCategory> findAllCategory(SessionUser user) {
        return tblCategoryMapper.finalAll(user.getOwnerId());
    }

	@Override
	public TblCategory selectByOwner(TblCategory cate) {
		return tblCategoryMapper.selectOne(cate);
	}

    @Override
    public boolean updateSort(CategorySortParam param) {
        return tblCategoryMapper.updateSort(param);
    }

}