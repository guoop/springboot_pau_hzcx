package com.soft.ware.rest.modular.handover.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.warpper.BaseControllerWarpper;
/**
 * 交接班controller
 * @author paulo
 * @Date 2019-03-20 11:46
 */
@RestController
@RequestMapping("/user")
public class HandoverController extends BaseController{
	@RequestMapping("handover")
	public Object getHandover(){
		
		return super.warpObject(new BaseControllerWarpper(new HashMap<String, Object>()) {
			
			@Override
			protected void warpTheMap(Map<String, Object> map) {
				
			}
		});
	}
	
	

}
