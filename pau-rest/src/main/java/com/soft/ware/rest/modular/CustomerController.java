package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.banner.model.TBanner;
import com.soft.ware.rest.modular.banner.service.TBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/customer/v1")
public class CustomerController extends BaseController {


    @Autowired
    private TBannerService bannerService;

    /**
     * 首页横幅
     * banner
     * @param owner
     * @return
     */
    @RequestMapping(value = "banner/list")
    public Object banners(SessionOwner owner){
        List<TBanner> list = bannerService.findBannerByOwner(owner);
        return render().set("list", list);
    }



    /**
     * 商品分类列表
     * @param user
     * @return
     */
    @RequestMapping(value = "/customer/v1/category/list")
    public Object category(SessionUser user){
       // List<TblCategory> list =  categoryService.findAllCategory(user);
        //return list;
        return  null;
    }


}
