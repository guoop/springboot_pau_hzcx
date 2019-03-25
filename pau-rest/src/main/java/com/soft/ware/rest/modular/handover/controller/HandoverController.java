package com.soft.ware.rest.modular.handover.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.support.HttpKit;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.HandOver;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.auth.wrapper.FailWrapper;
import com.soft.ware.rest.modular.auth.wrapper.SuccessWrapper;
import com.soft.ware.rest.modular.handover.service.IHandOverService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交接班controller
 * @author paulo
 * @Date 2019-03-20 11:46
 */
@RestController
@RequestMapping("/user")
public class HandoverController extends BaseController{
	@Autowired
	private IHandOverService overService;
	/**
	 * 通过owner字段获取交接班当前详细信息
	 * @param owner
	 * @return
	 */
	@RequestMapping("handover")
	public Object getHandover(HandoverParam param,SessionUser session,Page page){
		/*//添加page是为了在另外一个地方使用，当前controller可为空
		overService.getHandOver(param,session,page);*/
		HandOver ho = new HandOver();
		ho.setOwner(session.getOwner());
		ho = overService.selectOne(new EntityWrapper<>(ho));
		if(ToolUtil.isNotEmpty(ho)){
			return ho;
		}
		return null;
		
	}


	/**
	 * 交接班,记录
	 * @return
	 */
	@RequestMapping(value = "handover",method = RequestMethod.POST)
	public Object handover(SessionUser user, HandoverParam param){
		HandOver over = overService.over(user, param);
		if (over == null) {
			return super.warpObject(new FailWrapper());
		} else {
			return super.warpObject(new SuccessWrapper());
		}
	}
	
	

}
