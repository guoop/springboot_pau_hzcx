package com.soft.ware.rest.modular.owner_staff.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @RequestMapping(value = "staff/addOrUpdate", method = RequestMethod.POST)
    public Tip updateOrSave(@RequestBody @Valid StaffEditParam param, SessionUser sessionUser, BindingResult result) {
        Validator.valid(result);
        if (tOwnerStaffService.addOrUpdate(sessionUser, param)) {
            return new SuccessTip();
        }
        return new ErrorTip();
    }

    /**
     * 删除店员信息
     * @param param 店员id
     * @return
     */
    @RequestMapping(value = "staff/del", method = RequestMethod.POST)
    public Tip Delete(@RequestBody Map<String, Object> param, SessionUser sessionUser) throws Exception {
        if (tOwnerStaffService.delStaff(param, sessionUser)) {
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
        List<TOwnerStaff>  staffList = tOwnerStaffService.selectStaffByOwnerId(sessionUser.getOwnerId());
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
          TOwnerStaff sta = tOwnerStaffService.findByLoginName(sessionUser.getPhone());
          TOwnerStaff staff = tOwnerStaffService.selectById(id);
          Map<String,Object> mapResult = new HashMap<>();
          List<String> ids = new ArrayList<>();
          String function[] = sta.getFunctionList().split(",");
          List<Map<String,Object>> functionList = ParamUtils.getAllFunction(function);
          mapResult.put("functionList",functionList);
          String str[] = sta.getCategoryList().split(",");
          for(int i=0;i<str.length;i++){
              ids.add(str[i]);
          }
          List<TCategory> categoryList = itCategoryService.selectCategoryByIds(ids);
          mapResult.put("categoryList",categoryList);
          if(ToolUtil.isNotEmpty(staff)){
              mapResult.put("staff",staff);
              return new SuccessTip(mapResult);
          }else{
              return new SuccessTip(mapResult);
          }
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

