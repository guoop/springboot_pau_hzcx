package com.soft.ware.rest.modular.auth.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.common.persistence.dao.TblOrderAppMapper;
import com.soft.ware.rest.common.persistence.model.TblOrderApp;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblOrderAppService;
import com.soft.ware.rest.modular.auth.util.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TblOrderAppServiceImpl extends BaseService<TblOrderAppMapper,TblOrderApp> implements TblOrderAppService {

    @Resource
    private TblOrderAppMapper mapper;

    @Override
    public List<Map<String, Object>> findPage(SessionUser user, Page page, OrderPageParam param) {
        Integer count = mapper.selectCount(new EntityWrapper<>(new TblOrderApp().setOwner(user.getOwner())));
        page.setTotal(count);
        List<Map<String, Object>> maps = mapper.selectMapsPage(new RowBounds((int) page.getOffset(), page.getLimit()), new EntityWrapper<>(new TblOrderApp().setOwner(user.getOwner())));
        return maps;
    }
}