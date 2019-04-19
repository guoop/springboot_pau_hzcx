package com.soft.ware.rest.modular.owner_staff.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店员控制层
 */
@RestController
@RequestMapping("owner/v1")
public class StaffController extends BaseController {

    @Autowired
    private TOwnerStaffService tOwnerStaffService;

    @Autowired
    private ITOwnerService itOwnerService;

    @Autowired
    private ITCategoryService itCategoryService;

      @RequestMapping(value = "staff/addOrUpdate",method = RequestMethod.POST)
       public Tip updateOrSave(SessionUser sessionUser,  StaffEditParam param){
          if(tOwnerStaffService.addOrUpdate(sessionUser,param)){
              return new SuccessTip();
          }
           return new ErrorTip();
       }

    /**
     * 删除店员信息
     * @param id 店员id
     * @return
     */
    @RequestMapping(value = "staff/del",method = RequestMethod.GET)
       public Tip Delete(@RequestParam String id){
              if(tOwnerStaffService.deleteById(id)){
                  return new SuccessTip();
              }
              return new ErrorTip();
      }

    /**
     * 获取超市下的所有店员信息
     * @param sessionUser 当前登录的商户
     * @return
     */
      @RequestMapping(value = "staff/list")
      public Tip getList(SessionUser sessionUser){
        TOwnerStaff staff = new TOwnerStaff();
        staff.setOwnerId(sessionUser.getOwnerId());
        List<TOwnerStaff>  staffList = tOwnerStaffService.selectList(new EntityWrapper<>(staff));
        if(staffList.size() > 0){
            return new SuccessTip(staffList);
        }
            return new ErrorTip();
      }

    /**
     * 获取店员详情
     * @param id 店员id
     * @return
     */
      @RequestMapping("staff/detail")
      public Tip getStaffDetail(SessionUser sessionUser , String id){
          TOwnerStaff staff = tOwnerStaffService.selectById(id);
          if(ToolUtil.isNotEmpty(staff)){
              return new SuccessTip(staff);
          }
          TOwnerStaff sta = tOwnerStaffService.findByPhone(sessionUser.getPhone());
          if(ToolUtil.isNotEmpty(sta)){
              Map<String,Object> map = new HashMap<>();
              List<Map<String,Object>> functinListMap = new ArrayList<>();
              String function[] = sta.getFunctionList().split(",");
              List<Map<String,Object>> functionList = ParamUtils.getAllFunction(function);
              map.put("functionList",functionList);
              List<String> ids = new ArrayList<>();
              String str[] = sta.getCategoryList().split(",");
             for(int i=0;i<str.length;i++){
                 ids.add(str[i]);
             }
             List<TCategory> categoryList = itCategoryService.selectCategoryByIds(ids);
              map.put("categoryList",categoryList);
              return new SuccessTip(map);
          }
          return  new ErrorTip();
      }

    /**
     * 获取当前登录用户的基本信息
     * @param sessionUser 当前登录的用户基本信息
     * @// TODO: 2019/4/10  sessionUser.getId 有可能不是当前表的主键
     */
    @RequestMapping("staff/get-info")
    public Tip getStaffInfo(SessionUser sessionUser){
        TOwnerStaff staff = tOwnerStaffService.selectById(sessionUser.getId());
        if(ToolUtil.isNotEmpty(staff)){
            return new SuccessTip(staff);
        }
        return  new ErrorTip();
    }



}

