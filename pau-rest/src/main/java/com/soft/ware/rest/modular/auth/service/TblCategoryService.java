package com.soft.ware.rest.modular.auth.service;

import com.baomidou.mybatisplus.service.IService;
import com.soft.ware.rest.common.persistence.model.TblCategory;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;

import java.util.List;

public interface TblCategoryService extends IService<TblCategory> {


    List<TblCategory> findAllCategory(SessionUser staff);

}
