package com.soft.ware.rest.modular.goods.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.BeanMapUtils;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.model.TRepository;
import com.soft.ware.rest.modular.goods.service.*;
import com.soft.ware.rest.modular.goods_storage.model.TGoodsStorage;
import com.soft.ware.rest.modular.goods_storage.service.ITGoodsStorageService;
import com.soft.ware.rest.modular.icon.model.TIcon;
import com.soft.ware.rest.modular.icon.service.TIconService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.ITOwnerStaffService;
import com.soft.ware.rest.modular.promotion.model.TPromotion;
import com.soft.ware.rest.modular.promotion.service.TPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/owner/v1")
public class TGoodsController extends BaseController {
    /**
     * 商品分类服务
     */
    @Autowired
    private ITCategoryService itCategoryService;
    /**
     * 商品服务
     */
    @Autowired
    private ITGoodsService itGoodsService;
    /**
     * 库存服务
     */
    @Autowired
    private ITRepositoryService itRepositoryService;

    /**
     * 规格服务
     */
    @Autowired
    private ITSpecsService itSpecsService;

    /**
     * 单位服务
     */
    @Autowired
    private ITUnitService itUnitService;

    /**
     * 供应商服务
     */
    @Autowired
    private ITSupplierService itSupplierService;

    /**
     * 分类图标服务
     */
    @Autowired
    private TIconService iconService;
    /**
     * 商品库存服务
     */
    @Autowired
    private ITGoodsStorageService ITGoodsStorageService;
    /**
     * 促销服务
     */
    @Autowired
    private TPromotionService tPromotionService;

    @Autowired
    private ITOwnerStaffService staffService;

    @Autowired
    private ITRepositoryService repositoryService;

    /**
     * 获取分类列表
     * @param sessionUser  当前登录用户
     * @return
     */
    @RequestMapping("category/list")
    public Tip getCategoryList(@RequestParam Map<String,Object> param, SessionUser sessionUser){
        param.put("ownerId",sessionUser.getOwnerId());
       List<TCategory> list = itCategoryService.selectParentCategoryList(param);
       if(list.size() > 0){
           return new SuccessTip(list);
       }
        return new ErrorTip();
    }

    /**
     * 根据分类id获取分类详情
     * @param id
     * @return
     */
    @RequestMapping(value = "category/detail",method = RequestMethod.GET)
    public Tip getCategoryDetail(SessionUser user,@RequestParam String id){
        TCategory category = itCategoryService.selectById(id);
        category.setChildCategory(itCategoryService.selectList(new EntityWrapper<>(new TCategory().setPid(id))));
        return renderData(category);
    }

    /**
     * 新增或者编辑商品分类
     * @param category 分类
     * @param user 当前登录商户
     * @return
     */
    @RequestMapping(value = "category/addOrUpdate",method = RequestMethod.POST)
    public Tip addOrUpdate(@RequestBody TCategory category,SessionUser user){
        if (TCategory.is_system_1.equals(category.getIsSystem())) {
            return render(false, "系统类目不能编辑");
        }
        return render(itCategoryService.saveOrUpdate(user, category));
    }


    /**
     * 通过分类id 删除分类
     * @param m
     * @return
     */
    @RequestMapping("category/del")
    public Tip DelCategory(@RequestBody Map<String,Object> m,SessionUser user) throws Exception {
        Kv<String,Object> kv = Kv.toKv(m);
        String id = kv.requiredStr("id");
        Boolean force = kv.getBoolean("force", "yes");
        TCategory c = itCategoryService.selectById(id);
        if (!force) {
            List<Map<String, Object>> cs = itCategoryService.findChild(user, c, true);
            List<String> ids = cs.stream().map(s -> s.get("id").toString()).collect(Collectors.toList());
            int count = itGoodsService.selectCount(new EntityWrapper<>(new TGoods().setOwnerId(user.getOwnerId()).setIsDelete(TGoods.is_delete_0).setStatus(TGoods.status_1)).in("id", ids));
            if (count > 0) {
                return render(false, "存在上架商品，强制删除？");
            }

        }
        List<TOwnerStaff> list = staffService.findList(Kv.obj("ownerId", user.getOwnerId()));
        for (TOwnerStaff staff : list) {
            //删除员工的商品分类管理权限
            if (staff.getCategoryList().contains(id)) {
                staff.setCategoryList(staff.getCategoryList().replaceAll("," + id, ""));
                staff.setCategoryList(staff.getCategoryList().replaceAll(id + ",", ""));
                staffService.updateById(staff);
            }
        }
        c.setIsDelete(TCategory.is_system_1);
        return render(itCategoryService.updateById(c));
    }

    /**
     * 商品列表
     * @param param page当前页，categoryId 分类
     * @param user
     * @return
     */
    @RequestMapping("goods/list")
    public Tip getGoodsList(@RequestParam Map<String,Object> param,Page page ,SessionUser user) throws Exception {
        List<Map<String, Object>> list = itGoodsService.selectTGoodsListByMap(user, param, page);
        return render().set("data", list).set("xxxxx", "aaaaa");
    }
    @RequestMapping(value = "goods/detail",method = RequestMethod.GET,params = "id")
    public Tip getGoodsDetail(@RequestParam String id){
       Map<String,Object> goods = itGoodsService.findById(id);
         if(ToolUtil.isNotEmpty(goods)){
             return new SuccessTip(goods);
         }
         return new ErrorTip();
    }

    @RequestMapping(value = "goods/detail",method = RequestMethod.GET,params = "code")
    public Tip goods(@RequestParam String code,SessionUser user) throws Exception {
        List<Map<String, Object>> goods = itGoodsService.findByCode(user,code);
        return renderData(goods);
    }

    /**
     * 根据商品编码从商品库查询
     * @param code
     * @return
     */
    @RequestMapping(value = "goods/repository",method = RequestMethod.GET)
    public Tip repository(String code){
        List<Map<String, Object>> maps = repositoryService.findMaps(Kv.by("code", code));
        return renderData(maps);
    }
    /**
     * 获取系统内置商品分类图标
     * @param sessionUser
     * @return 图标列表
     */
    @RequestMapping("icon/list")
    public Tip getIcon(SessionUser sessionUser){
        List list = iconService.selectList(new EntityWrapper<TIcon>());
        if(list.size() > 0){
            return new SuccessTip(list);
        }
        return new ErrorTip();
    }

    /**
     * 分类排序
     * @param categorySortParam id 主键,status 状态,,weights权重
     * @return
     */
     @RequestMapping("category/sort")
     public Tip categorySort(@RequestBody CategorySortParam categorySortParam){
         if(itCategoryService.updateOrSort(categorySortParam)){
             return new SuccessTip();
         }
        return new ErrorTip();
     }

    /**
     * 添加或者更新商品
     * @param goods
     * @return
     */
    @RequestMapping(value = "goods/addOrUpdate",method = RequestMethod.POST)
    public Tip goodsEdit(@RequestBody Map<String,Object> goods,SessionUser user) throws Exception {
        boolean isSuccess = false;
        TGoods g = BeanMapUtils.toObject(goods, TGoods.class);
        itGoodsService.addByManual(user, g, null);
        /*if (g.getId() != null){
             isSuccess = itGoodsService.updateById(g);
         }else{
             isSuccess = itGoodsService.insert(g);
         }
         if(isSuccess){
             return  new SuccessTip();
         }*/
        return new ErrorTip();
    }

    /**
     * 扫描添加
     * @param goods
     * @return
     */
    @RequestMapping("goods/addByScan")
    public Tip addByScan(@RequestBody Map goods,SessionUser user) throws Exception {
        TGoods g = BeanMapUtils.toObject(goods, TGoods.class, true);
        TGoodsStorage s = BeanMapUtils.toObject(goods, TGoodsStorage.class, true);
        boolean b = itGoodsService.addByScan(user, g, s);
        return render(b);
    }

    /**
     * 商品置顶或者是商品上下架
     * @param param id 商品主键,flag 是否置顶 yes置顶 No 不置顶| status 商品状态
     * @param path top是商品置顶，upOrDown是商品上下架
     * @return
     */
    @RequestMapping("goods/{path}")
    public Tip goodsTop(@PathVariable("path") String path , @RequestBody Map<String,Object> param){
        if(path.equals("top")){
            if ("yes".equals(param.get("flag"))) {
                itGoodsService.top(param.get("id") + "", new Date());
            } else {
                itGoodsService.top(param.get("id") + "", null);
            }
            return render();
        }else{
            if(itGoodsService.updateGoodsTopTimeOrStatus(param)){
                return new SuccessTip();
            }
        }

        return new ErrorTip();
    }

    /**
     * 删除商品
     * @param id 主键id
     * @return
     */
    @RequestMapping("goods/del")
    public Tip goodsDel(String id){
        if(itGoodsService.deleteById(id)){
            return new SuccessTip();
        }
        return new ErrorTip();
    }

    /**
     * 根据商品编码查询商品库
     * @param code 商品编码
     * @return
     */
    public Tip selectRepositoryByGoodsCode(String code){
       TRepository repository = itRepositoryService.selectRepositoryByGoodsCode(code);
       if(ToolUtil.isNotEmpty(repository)){
           return new SuccessTip(repository);
       }
        return new ErrorTip();
    }

    /**
     * 商品调价 id商品调价，code商品编码，unitId商品单位，商品单价，is_promotion是否促销，promotion_price促销价格，promotion_endtime促销结束时间
     * @param param
     * @return 
     * @// TODO: 2019/4/15 商品单位在更新的时候暂时当成unitName使用
     */
    @RequestMapping(value = "goods/change-price",method = RequestMethod.POST)
    public Tip goodsChangePrice(@RequestBody Map<String,Object> param){
        boolean isSuccess = false;
        TGoods tGoods = new TGoods();
        tGoods.setId(param.get("id").toString());
        tGoods.setCode(param.get("code").toString());
        tGoods.setUnitId(param.get("unitId").toString());
        tGoods.setPrice(BigDecimal.valueOf(Double.valueOf(param.get("price").toString())));
        tGoods.setIsPromotion(Integer.valueOf(param.get("isPromotion").toString()));
        isSuccess = itGoodsService.updateById(tGoods);
        if(Integer.valueOf(param.get("isPromotion").toString()) == 1){
            TPromotion tPromotion = new TPromotion();
            tPromotion.setEndTime(DateUtil.parseDate(param.get("endTime").toString()));
            tPromotion.setMoney(BigDecimal.valueOf(Double.valueOf(param.get("promotionPrice").toString())));
            tPromotion.setGoodsId(param.get("id").toString());
            isSuccess = tPromotionService.updateByGoodsId(tPromotion);
        }
        if(isSuccess){
            return new SuccessTip();
        }
        return  new ErrorTip();
    }
    @RequestMapping(value = "storage/change",method = RequestMethod.POST)
    public Tip goodsChangeStorage(SessionUser sessionUser,TGoodsStorage goodsStorage){
        boolean isSuccess = false;
        if(ToolUtil.isNotEmpty(goodsStorage.getId())){
            isSuccess = ITGoodsStorageService.updateById(goodsStorage);
        }else{
            goodsStorage.setId(IdGenerator.getId());
            ITGoodsStorageService.insert(goodsStorage);
        }
        return new ErrorTip();
    }






}
