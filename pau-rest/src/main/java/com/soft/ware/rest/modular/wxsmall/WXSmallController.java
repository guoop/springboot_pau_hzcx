package com.soft.ware.rest.modular.wxsmall;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.rest.modular.auth.util.WXContants;

@Controller
public class WXSmallController extends BaseController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private 
	/**
	 * 获取openId
	 * @param code  
	 * @return openId
	 */
	@RequestMapping("/owner/share/wx_identifier")
	public String getOpenId(String code){
		
		String result = restTemplate.getForObject(WXContants.WX_LOGIN+"?appid=APPID&secret=SECRET&js_code=+"+code+"+&grant_type=authorization_code",String.class, "json");
		
		return null;
	}
	

}
