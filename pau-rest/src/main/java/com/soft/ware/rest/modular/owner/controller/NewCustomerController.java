package com.soft.ware.rest.modular.owner.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.common.persistence.model.TblBanner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.service.TblBannerService;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewCustomerController extends BaseController {


    private TblBannerService bannerService;

    /**
     * banner
     * @param user
     * @return
     */
    @RequestMapping(value = "banner/list")
    public Object banners(SessionUser user){
        List<TblBanner> list = bannerService.findBannerByOwner(user.getOwner());
        Map<String, Object> map = new HashMap<>();
        map.put("code", SUCCESS);
        return list;
    }



}
