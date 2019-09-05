package com.soft.ware.modular.system.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.shiro.ShiroKit;
import com.soft.ware.modular.system.model.User;
import com.soft.ware.modular.system.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.soft.ware.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.soft.ware.modular.system.model.TbMember;
import com.soft.ware.modular.system.service.ITbMemberService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器
 * @author paulo
 * @Date 2019-09-02 15:05:06
 */
@Controller
@RequestMapping("/tbMember")
public class TbMemberController extends BaseController {

    private String PREFIX = "/system/tbMember/";

    @Autowired
    private ITbMemberService tbMemberService;

    @Autowired
    private IUserService iUserService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tbMember.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/tbMember_add")
    public String tbMemberAdd(Model model) {
        Integer  memberId = (Integer) ShiroKit.getSession().getAttribute("memberId");
        List<User> userList = iUserService.getUserListByMemberId(memberId);
        model.addAttribute("userList",userList);
        return PREFIX + "tbMember_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/tbMember_update/{tbMemberId}")
    public String tbMemberUpdate(@PathVariable Integer tbMemberId, Model model) {
        TbMember tbMember = tbMemberService.selectById(tbMemberId);
        model.addAttribute("item",tbMember);
        LogObjectHolder.me().set(tbMember);
        return PREFIX + "tbMember_edit.html";
    }

    /**
     * 获取列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Map map = new HashMap<>();
        map.put("userId",ShiroKit.getUser().id);
        map.put("condition",condition);
        return tbMemberService.memberList(map);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TbMember tbMember) {
        tbMember.setCreateTime(new Date());
        tbMember.setCreater(ShiroKit.getUser().name);
        tbMemberService.insert(tbMember);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tbMemberId) {
        tbMemberService.deleteById(tbMemberId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TbMember tbMember) {
        tbMemberService.updateById(tbMember);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{tbMemberId}")
    @ResponseBody
    public Object detail(@PathVariable("tbMemberId") Integer tbMemberId) {
        return tbMemberService.selectById(tbMemberId);
    }

    /**
     * 查询当前用户
     */
    @RequestMapping(value = "/user/list")
    @ResponseBody
        public Object userIdByList(Integer userId){
        System.out.println(userId);
        return ERROR;
    }


}
