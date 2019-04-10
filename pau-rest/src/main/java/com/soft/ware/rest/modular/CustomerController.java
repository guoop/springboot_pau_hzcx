package com.soft.ware.rest.modular;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.base.warpper.ListWrapper;
import com.soft.ware.core.util.Kv;
import com.soft.ware.rest.common.persistence.model.TblOrder;
import com.soft.ware.rest.modular.address.model.TAddress;
import com.soft.ware.rest.modular.address.service.ITAddressService;
import com.soft.ware.rest.modular.auth.controller.dto.GoodsPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.Id;
import com.soft.ware.rest.modular.auth.controller.dto.OrderPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.banner.model.TBanner;
import com.soft.ware.rest.modular.banner.service.TBannerService;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.goods.service.ITGoodsService;
import com.soft.ware.rest.modular.order.service.ITOrderService;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.question.model.TQuestion;
import com.soft.ware.rest.modular.question.service.ITQuestionService;
import com.soft.ware.rest.modular.wx_app.service.ISWxAppService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private ITOwnerService ownerService;

    @Autowired
    private ISWxAppService appService;

    @Autowired
    private ITOrderService orderService;

    @Autowired
    private ITAddressService addressService;

    @Autowired
    private ITQuestionService questionService;

    /**
     * 首页横幅
     * banner
     * @param owner
     * @return
     */
    @RequestMapping(value = "banner/list",method = RequestMethod.GET)
    public Tip banners(SessionUser owner){
        List<TBanner> list = bannerService.findBannerByOwner(owner);
        return render().set("list", list);
    }



    /**
     * 商品分类列表
     * @param user
     * @return
     */
    @RequestMapping(value = "category/list",method = RequestMethod.GET)
    public Tip category(SessionUser user){
        List<Map<String, Object>> list = categoryService.findMaps(Kv.by("pid", null).set("ownerId", user.getOwnerId()));
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
    @RequestMapping(value = "goods/{id}",method = RequestMethod.GET)
    public Object goods(@PathVariable String id) {
        List<Map<String, Object>> list = goodsService.find(Kv.by("id", id));
        return render().setOne("goods", list);
    }


    /**
     * 商品详情
     * @param id
     * @return
     */
    @RequestMapping(value = "goods/{id}",method = RequestMethod.GET,params = {"flag=goodsNO"})
    public Object goodsByCode(@PathVariable String id) {
        List<Map<String,Object>> list = goodsService.find(Kv.by("owner",id));
        return render().setOne("goods", list);
    }

    /**
     * 商户信息查询
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "shop")
    public Object owner(SessionUser user) {
        Map<String, Object> owner = ownerService.findMap(Kv.by("id", user.getOwnerId()));
        Map<String, Object> app = appService.findMap(Kv.by("ownerId", user.getOwnerId()));
        return render().set("owner", owner).merge("owner", app);
    }




    /**
     * 订单列表查询
     * @param user
     * @param page
     * @param param
     * @return
     */
    @RequestMapping(value = "orders",method = RequestMethod.GET)
    public Object orders(SessionUser user,Page page, OrderPageParam param){
        // 所有订单
        if ("all".equals(param.getStatus())) {
            param.setStatus("-2,-1,0,1,2,3,10");
        } else if ("pay".equals(param.getStatus())) {
            // 待付款
            param.setStatus("0");
        } else if ("receive".equals(param.getStatus())) {
            // 待收货
            param.setStatus("1,2,10");
        } else if ("done".equals(param.getStatus())) {
            // 已完成
            param.setStatus("3");
        } else if ("cancel".equals(param.getStatus())) {
            // 已取消
            param.setStatus("-1,-2");
        } else {
            param.setStatus(Integer.MAX_VALUE+"");
        }
        List<Map> list = orderService.findPage(user, page, param, TblOrder.SOURCE_0, TblOrder.SOURCE_2);
        return warpObject(new ListWrapper(list));
    }



    /**
     * 订单详情
     * @param user
     * @param no
     * @return
     */
    @RequestMapping(value = "orders/{no}",method = RequestMethod.GET)
    public Tip orders(SessionUser user,@PathVariable String no) {
        Map<String, Object> map = orderService.findMap(Kv.obj("orderNo", no).set("user", user));
        return render().set("order", map);
    }


    /**
     * 收货地址列表
     * @param user
     * @return
     */
    @RequestMapping(value = "address",method = RequestMethod.GET)
    public Object address(SessionUser user) {
        List<Map<String, Object>> list = addressService.findMaps(Kv.obj("ownerId", user.getOwnerId()).set("creater", user.getOpenId()).set("isDelete", TAddress.is_delete_0).set("orderBy"," is_default desc, created_time desc "));
        return render(list);
    }

    /**
     * 收货地址详情
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "address/{id}",method = RequestMethod.GET)
    public Tip address(SessionUser user, @PathVariable String id){
        Map<String, Object> address = addressService.findMap(Kv.obj().set("id", id).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
        return render().set("address", address);
    }

    /**
     * 删除收货地址
     * @param user
     * @param id
     * @return
     */
    @RequestMapping(value = "address/del",method = RequestMethod.POST)
    public Object addressDel(SessionUser user, @RequestBody Id id, BindingResult result) throws Exception {
        Validator.valid(result);
        Map<String, Object> address = addressService.findMap(Kv.obj().set("id", id.getId()).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
        TAddress a = BeanMapUtils.toObject(address, TAddress.class, true);
        addressService.deleteById(user, a);
        return render();
    }



    /**
     * 添加/编辑 用户地址
     * @param user
     * @param address
     * @return
     */
    @RequestMapping(value = "address/man",method = RequestMethod.POST)
    public Object address(SessionUser user,@RequestBody TAddress address) throws Exception {
        if (address.getId() == null) {
            address.setOwnerId(user.getOwnerId());
            address.setCreater(user.getOpenId());
            address.setCreatedTime(new Date());
            boolean b = addressService.addAddress(user, address);
            return render(b);
        }else{
            Map<String, Object> addr = addressService.findMap(Kv.obj().set("id", address.getId()).set("creater", user.getOpenId()).set("ownerId", user.getOwnerId()));
            TAddress old = BeanMapUtils.toObject(addr, TAddress.class, true);
            old.setName(address.getName());
            old.setProvince(address.getProvince());
            old.setDetail(address.getDetail());
            old.setPhone(address.getPhone());
            old.setIsDefault(address.getIsDefault());
            boolean b = addressService.updateAddress(user, address);
            return render(b);
        }
    }


    /**
     * 意见反馈
     * @param user
     * @param question
     */
    @RequestMapping(value = "/customer/v1/question",method = RequestMethod.POST)
    public Tip question(SessionUser user,@RequestBody TQuestion question){
        question.setOpenId(user.getOpenId());
        question.setOwner(user.getOwnerId());
        question.setCreatedAt(new Date());

        boolean b = questionService.add(user,question);
        return render(b);
    }



}
