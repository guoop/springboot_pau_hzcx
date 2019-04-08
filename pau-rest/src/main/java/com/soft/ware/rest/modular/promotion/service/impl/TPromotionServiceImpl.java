package com.soft.ware.rest.modular.promotion.service.impl;
import com.soft.ware.core.base.controller.BaseService;
import com.soft.ware.rest.modular.promotion.dao.TPromotionMapper;
import com.soft.ware.rest.modular.promotion.model.TPromotion;
import com.soft.ware.rest.modular.promotion.service.TPromotionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
@Service
@Transactional
public class TPromotionServiceImpl extends BaseService<TPromotionMapper,TPromotion> implements TPromotionService {

    @Resource
    private TPromotionMapper mapper;

}