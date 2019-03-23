package com.soft.ware.rest.modular.wxsmall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.service.TblOwnerStaffService;
import com.soft.ware.rest.modular.auth.util.WXContants;

@Controller
@RequestMapping("/owner")
public class WXSmallController extends BaseController {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private TblOwnerService tblOwnerService;
	@Autowired
	private TblOwnerStaffService tblOwnerStaffService;
	
	/**
	 * 获取openId
	 * @param code  
	 * @return openId
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/share/wx_identifier")
	@ResponseBody
	public Object getOpenId(String code){
		Map<String , Object> result= new HashMap<String,Object>();
		String owner = (String) HttpKit.getRequest().getAttribute("owner");
		if(ToolUtil.isEmpty(owner)){
			throw new PauException(BizExceptionEnum.PARAME_ERROR);
		}
		TblOwner tblOwner = new TblOwner();
		tblOwner.setOwner(owner);
		tblOwner = tblOwnerService.selectOne(new EntityWrapper<>(tblOwner));
		if(ToolUtil.isNotEmpty(owner)){
		   result = restTemplate.getForObject(WXContants.WX_LOGIN+"?appid=+"+tblOwner.getAppId()+"&secret="+tblOwner.getAppSecret()+"&js_code=+"+code+"+&grant_type=authorization_code",Map.class, "json");
		}
		return result.get("openid");
		
	}
	/**
	 * 商家端发送手机验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("/share/code")
	@ResponseBody
	public Tip getPhoneCode(String phone){
		String msgCode = ToolUtil.getRandomString(6);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("", msgCode);
		ResponseEntity<String> result= restTemplate.postForEntity(WXContants.TENCENTMSG_GATAWAY, map, String.class);
		if(ToolUtil.isNotEmpty(result.getBody())){
			return new ErrorTip(601,"短信地址请求失败");
		}
		return SUCCESS_TIP;
	}
	
	/**
	 * 登录商家版小程序
	 * @param phone 手机号
	 * @param code  验证码
	 * @return 用户信息以及token值
	 */
	@RequestMapping("/share/login")
	@ResponseBody
	public Object login(String phone,String code){
		
		return null;
	}
	/**
	 * 获取登录用户的基本信息
	 * @return 用户信息
	 */
	@RequestMapping("/v1/self")
	@ResponseBody
	public Object getUserInfo(){
		String owner = (String) HttpKit.getRequest().getSession().getAttribute("owner");
		TblOwnerStaff tblOwner = new TblOwnerStaff();
		if(ToolUtil.isNotEmpty(owner)){
			tblOwner.setOwner(owner);
			tblOwner = tblOwnerStaffService.selectOne(new EntityWrapper<>(tblOwner));
		}else{
			return new ErrorTip(602,"登录异常");
		}
		return tblOwner;
		
	} 
	
	/**
	 * 退出登录
	 */
	@RequestMapping("/v1/logout")
	@ResponseBody
	public Object logout(){
		return null;
	}
	
	/**
	 * 更新商户信息
	 * @param owner 商户类
	 */
   	 @RequestMapping("v1/auth/info")
	 @ResponseBody
     public Tip updateOwner(TblOwner owner){
   		 if(ToolUtil.isNotEmpty(owner.getId())){
   			if(tblOwnerService.updateById(owner)){
   				return SUCCESS_TIP;
   			}{
   				return new ErrorTip(603,"更新商户信息失败");
   			}
   		 }
		return null;
     }	
   	 /**
   	  * 获取店员列表
   	  */
   	 @RequestMapping("v1/auth/staff/list")
	 @ResponseBody
   	 public Object getStaffList(){
   		Wrapper<TblOwnerStaff> wrapper = new EntityWrapper<TblOwnerStaff>();
   		List<TblOwnerStaff> list = tblOwnerStaffService.selectList(wrapper);
   		if(list.size()>0){
   			return list;
   		}else{
   			return new ErrorTip(604, "查询店员列表错误");
   		}
   		 
   	 }
   	 /**
   	  * 获取店员详情
   	  * @param id 店员id
   	  */
   	@RequestMapping("v2/auth/staff/index")
	@ResponseBody
	public Object getStaffDetail(String id){
   		TblOwnerStaff tblOwnerStaff = tblOwnerStaffService.selectById(id);
   		if(ToolUtil.isEmpty(tblOwnerStaff)){
   			return new ErrorTip(605,"查询店员详情错误");
   		}{
   			return tblOwnerStaff;
   		}
   	} 
   	/**
   	 * 添加或者编辑店员信息
   	 * @return 
   	 */
   	@RequestMapping("/v2/auth/staff/man")
	@ResponseBody
   	public Tip addOrUpdate(@RequestBody TblOwnerStaff tblOwnerStaff){
   		Boolean isSuccess;
   		if(ToolUtil.isEmpty(tblOwnerStaff)){
   			if(ToolUtil.isEmpty(tblOwnerStaff.getId())){
   				 isSuccess = tblOwnerStaffService.updateAllColumnById(tblOwnerStaff);
   			}else{
   				isSuccess = tblOwnerStaffService.insert(tblOwnerStaff);
   			}
   			if(isSuccess){
   				return SUCCESS_TIP;
   			}else{
   				return new ErrorTip(608, "插入或者更新失败");
   			}
   		}
   		return new ErrorTip(604,"参数为空");
   	}
   	
   	/**
   	 * 删除店员
   	 */
   	@RequestMapping("v1/auth/staff/del")
   	@ResponseBody
   	public Tip del(String id){
   		if(ToolUtil.isEmpty(id)){
   			return new ErrorTip(605, "参数不能为空");
   		}
   		if(tblOwnerStaffService.deleteById(id)){
   			return SUCCESS_TIP;
   		};
   		return new ErrorTip(606, "删除失败");
   	}
   	
   	
   	
   	
}
