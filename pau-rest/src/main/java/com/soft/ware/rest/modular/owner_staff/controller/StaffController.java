package com.soft.ware.rest.modular.owner_staff.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.controller.dto.StaffEditParam;
import com.soft.ware.rest.modular.auth.util.ParamUtils;
import com.soft.ware.rest.modular.auth.validator.Validator;
import com.soft.ware.rest.modular.goods.model.TCategory;
import com.soft.ware.rest.modular.goods.service.ITCategoryService;
import com.soft.ware.rest.modular.owner.service.ITOwnerService;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.service.ITOwnerStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 店员控制层
 */
@RestController
@RequestMapping("owner/v1")
public class StaffController extends BaseController {

    @Autowired
    private ITOwnerStaffService tOwnerStaffService;

    @Autowired
    private ITOwnerService itOwnerService;

    @Autowired
    private ITCategoryService itCategoryService;

    /**
     * 编辑店员信息
     * @param param
     * @param sessionUser
     * @param result
     * @return
     */
    @RequestMapping(value = "staff/addOrUpdate", method = RequestMethod.POST)
    public Tip updateOrSave(@RequestBody @Valid StaffEditParam param, SessionUser sessionUser, BindingResult result) {
        Validator.valid(result);
        if (TOwnerStaff.shopkeeperFlag.equals(param.getFunctionList())) {
            return render(false, "非法操作");
        }
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
      public Tip getStaffDetail(SessionUser user , String id){
          TOwnerStaff sta = tOwnerStaffService.findByLoginName(user.getPhone());
          List<TCategory> categoryList;
          List<Map<String,Object>> functionList;
          if (TOwnerStaff.shopkeeperFlag.equals(sta.getFunctionList())) {
              //商品分类列表 店主
              categoryList = itCategoryService.selectParentCategoryList(Kv.obj("ownerId", user.getOwnerId()));
              functionList = ParamUtils.getAllFunction(StaffEditParam.kvs.keyList().toArray(new String[]{}));
          } else {
              //商品分类列表
              categoryList = itCategoryService.selectCategoryByIds(sta.getCategoryList().split(","));
              //权限列表
              String[] function = sta.getFunctionList().split(",");
              functionList = ParamUtils.getAllFunction(function);
          }
          TOwnerStaff staff = tOwnerStaffService.selectById(id);
          if (staff == null || !staff.getOwnerId().equals(user.getOwnerId())) {
              render(false, "没有权限");
          }
          return render().set("data", Kv.obj("functionList", functionList).set("categoryList", categoryList).set("staff", staff));
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

