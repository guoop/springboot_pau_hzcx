package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.base.warpper.ListWrapper;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.banner.model.TBanner;
import com.soft.ware.rest.modular.banner.service.TBannerService;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/customer/v1")
public class CustomerController extends BaseController {


    @Autowired
    private TBannerService bannerService;

    @Autowired
    private ITCategoryService categoryService;

    @Autowired
    private ITGoodsService goodsService;

    /**
     * 首页横幅
     * banner
     * @param owner
     * @return
     */
    @RequestMapping(value = "banner/list")
    public Tip banners(SessionUser owner){
        List<TBanner> list = bannerService.findBannerByOwner(owner);
        return render().set("list", list);
    }



    /**
     * 商品分类列表
     * @param user
     * @return
     */
    @RequestMapping(value = "category/list")
    public Tip category(SessionUser user){
        List<TCategory> list =  categoryService.findAllCategory(user);
        return render().set("list", list);
    }


    /**
     * 商品列表
     * @param param
     * @param user
     * @param page
     * @return
     */
    @RequestMapping(value = "goods/list",method = RequestMethod.GET)
    public Tip goodsPage(GoodsPageParam param, SessionUser user, Page page) throws WxErrorException {
        List<Map> list = goodsService.findPage(user, page, param);
        return render().set("list", list);
    }


    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "goods/{id}")
    public Object goods(@PathVariable String id) throws Exception {
        List<Map<String, Object>> list = goodsService.find(Kv.by("id", id));
        return render().setOne("data", list);
    }


    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "goods/{id}",method = RequestMethod.GET,params = {"flag=goodsNO"})
    public Object goodsByCode(@PathVariable String id) throws Exception {
        List<Map<String,Object>> goods = goodsService.find(Kv.by("owner",id));
        List<Map<String, Object>> maps = BeanMapUtils.toMap(true, goods);
        return warpObject(new ListWrapper(maps));
    }





}
