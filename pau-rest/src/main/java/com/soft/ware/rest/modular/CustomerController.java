package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.modular.auth.controller.dto.SessionOwner;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.banner.model.TBanner;
import com.soft.ware.rest.modular.banner.service.TBannerService;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping(value = "/customer/v1")
public class CustomerController extends BaseController {


    @Autowired
    private TBannerService bannerService;

    @Autowired
    private ITCategoryService categoryService;

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
    @RequestMapping(value = "category/list")
    public Object category(SessionUser user){
        List<TCategory> list =  categoryService.findAllCategory(user);
        return render().set("list", list);
    }


}
