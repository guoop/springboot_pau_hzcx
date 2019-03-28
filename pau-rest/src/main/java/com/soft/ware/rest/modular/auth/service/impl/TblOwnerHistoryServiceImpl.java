package com.soft.ware.rest.modular.auth.service.impl;

import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOwnerHistoryMapper;
import com.soft.ware.rest.common.persistence.model.TblOwnerHistory;
import com.soft.ware.rest.modular.auth.service.TblOwnerHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TblOwnerHistoryServiceImpl extends BaseService<TblOwnerHistoryMapper,TblOwnerHistory> implements TblOwnerHistoryService {

    @Resource
    private TblOwnerHistoryMapper tblOwnerHistoryMapper;

}