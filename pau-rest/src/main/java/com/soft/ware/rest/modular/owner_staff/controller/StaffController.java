package com.soft.ware.rest.modular.owner_staff.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.IdGenerator;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.TOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 店员控制层
 */
@RestController
@RequestMapping("staff/v1")
public class StaffController extends BaseController {

    @Autowired
    private TOwnerStaffService tOwnerStaffService;

      @RequestMapping(value = "/updateOrAdd",method = RequestMethod.POST)
       public Tip updateOrSave(SessionUser sessionUser, TOwnerStaff ownerStaff){
          Boolean isSuccess = false;
          if(ToolUtil.isNotEmpty(ownerStaff)){
              if(ToolUtil.isNotEmpty(ownerStaff.getId())){
                     isSuccess = tOwnerStaffService.updateById(ownerStaff);
              }else{
                  ownerStaff.setId(IdGenerator.getId());
                  isSuccess = tOwnerStaffService.insert(ownerStaff);
              }
              if(isSuccess){
                  return  new SuccessTip();
              }
          }
           return new ErrorTip();
       }

    /**
     * 删除店员信息
     * @param id 店员id
     * @return
     */
    @RequestMapping(value = "del",method = RequestMethod.GET)
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
      @RequestMapping(value = "list")
      public Tip getList(SessionUser sessionUser){
        TOwnerStaff staff = new TOwnerStaff();
        staff.setPassword(sessionUser.getOwnerId());
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
      @RequestMapping("detail")
      public Tip getStaffDetail(@RequestParam String id){
          TOwnerStaff staff = tOwnerStaffService.selectById(id);
          if(ToolUtil.isNotEmpty(staff)){
              return new SuccessTip(staff);
          }
          return  new ErrorTip();
      }

    /**
     * 获取当前登录用户的基本信息
     * @param sessionUser 当前登录的用户基本信息
     * @// TODO: 2019/4/10  sessionUser.getId 有可能不是当前表的主键
     */
    public Tip getStaffInfo(SessionUser sessionUser){
        TOwnerStaff staff = tOwnerStaffService.selectById(sessionUser.getId());
        if(ToolUtil.isNotEmpty(staff)){
            return new SuccessTip(staff);
        }
        return  new ErrorTip();
    }



}

