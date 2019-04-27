package com.soft.ware.rest.modular.goods.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.CategorySortParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.model.TGoods;
import com.soft.ware.rest.modular.goods.model.TRepository;
import com.soft.ware.rest.modular.goods.service.*;
import com.soft.ware.rest.modular.goods_storage.model.TGoodsStorage;
import com.soft.ware.rest.modular.goods_storage.service.TGoodsStorageService;
import com.soft.ware.rest.modular.icon.model.TIcon;
import com.soft.ware.rest.modular.icon.service.TIconService;
import com.soft.ware.rest.modular.promotion.model.TPromotion;
import com.soft.ware.rest.modular.promotion.service.TPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owner/v1")
public class TGoodsController {
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
    private TGoodsStorageService tGoodsStorageService;
    /**
     * 促销服务
     */
    @Autowired
    private TPromotionService tPromotionService;
    /**
     * 获取分类列表
     * @param sessionUser  当前登录用户
     * @return
     */
    @RequestMapping("category/list")
    public Tip getCategoryList(@RequestParam Map<String,Object> param, Page page, SessionUser sessionUser){
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
    @RequestMapping(value = "category/detail")
    public Tip getCategoryDetail(@RequestParam String id){
        TCategory category = itCategoryService.selectById(id);
        if(ToolUtil.isNotEmpty(category)){
            return new SuccessTip(category);
        }
        return new ErrorTip();
    }

    /**
     * 新增或者编辑商品分类
     * @param category 分类
     * @param sessionUser 当前登录商户
     * @return
     */
    @RequestMapping("category/addOrUpdate")
    public Tip addOrUpdate(@RequestParam TCategory category,SessionUser sessionUser){
        boolean isSuccess = false;
            if(ToolUtil.isNotEmpty(category.getId())){
                isSuccess =  itCategoryService.updateById(category);
            }else{
                isSuccess = itCategoryService.insert(category);
            }
            if(isSuccess){
                return new SuccessTip();
            }
            return  new ErrorTip();
    }

    /**
     * 通过分类id 删除分类
     * @param id
     * @return
     */
    @RequestMapping("category/del")
    public Tip DelCategory(@RequestParam String id){
        if(itCategoryService.deleteById(id)){
            return new SuccessTip();
        }
        return new ErrorTip();
    }

    /**
     * 商品列表
     * @param param page当前页，categoryId 分类
     * @param sessionUser
     * @return
     */
    @RequestMapping("goods/list")
    public Tip getGoodsList(@RequestParam Map<String,Object> param,Page page ,SessionUser sessionUser) throws ParseException {
        param.put("owner_id",sessionUser.getOwnerId());
        page.setPage(Long.valueOf(param.get("page").toString()));
        List<Map<String,Object>>  goodsList =  itGoodsService.selectTGoodsListByMap(param,page);
        if( goodsList.size()== 0||goodsList.size() > 0 ){
            return new SuccessTip(goodsList);
        }
        return  new ErrorTip();
    }
    @RequestMapping("goods/detail")
    public Tip getGoodsDetail(@RequestParam String id){
       HashMap<String,Object> goods = itGoodsService.findById(id);
         if(ToolUtil.isNotEmpty(goods)){
             return new SuccessTip(goods);
         }
         return new ErrorTip();
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
     public Tip categorySort(@RequestParam CategorySortParam categorySortParam){
         if(itCategoryService.updateOrSort(categorySortParam)){
             return new SuccessTip();
         };
        return new ErrorTip();
     }

    /**
     * 添加或者更新商品
     * @param goods
     * @return
     */
    @RequestMapping("goods/addOrUpdate")
    public Tip goodsEdit(@RequestParam TGoods goods){
         boolean isSuccess = false;
         if (goods.getId() != null){
             isSuccess = itGoodsService.updateById(goods);
         }else{
             isSuccess = itGoodsService.insert(goods);
         }
         if(isSuccess){
             return  new SuccessTip();
         }
         return new ErrorTip();
    }

    /**
     * 扫描添加
     * @param goods
     * @return
     */
    @RequestMapping("goods/addByScan")
    public Tip addByScan(@RequestParam TGoods goods){
        if(itGoodsService.insert(goods)){
            return new SuccessTip();
        }
        return new ErrorTip();
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
            if(itGoodsService.updateGoodsTopTimeOrStatus(param)){

                return  new SuccessTip();
            }
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
    public Tip goodsChangeStorage(TGoodsStorage goodsStorage){
        boolean isSuccess = false;
        if(ToolUtil.isNotEmpty(goodsStorage.getId())){
            isSuccess = tGoodsStorageService.updateById(goodsStorage);
        }else{
            goodsStorage.setId(IdGenerator.getId());

            tGoodsStorageService.insert(goodsStorage);
        }

        return new ErrorTip();
    }






}
