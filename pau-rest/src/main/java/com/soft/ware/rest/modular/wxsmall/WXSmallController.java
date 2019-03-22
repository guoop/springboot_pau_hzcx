package com.soft.ware.rest.modular.wxsmall;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.service.TblOwnerService;
import com.soft.ware.rest.modular.auth.util.WXContants;

@Controller
@RequestMapping("/owner")
public class WXSmallController extends BaseController {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	TblOwnerService tblOwnerService;
	
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
	@RequestMapping("/share/code")
	@ResponseBody
	public Tip getPhoneCode(String phone){
		String msgCode = ToolUtil.getRandomString(6);
		Map<String,Object> map = new HashMap<String, Object>();
		String result = restTemplate.postForObject(WXContants.TENCENTMSG_GATAWAY, map, String.class);
		if(ToolUtil.isEmpty(result)){
			return new ErrorTip(601,"短信地址请求失败");
		}
		return SUCCESS_TIP;
	}
	

}
