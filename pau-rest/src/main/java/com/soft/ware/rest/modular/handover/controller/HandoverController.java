package com.soft.ware.rest.modular.handover.controller;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.warpper.BaseControllerWarpper;
import com.soft.ware.core.exception.PauException;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import com.soft.ware.rest.common.persistence.model.HandOver;
import com.soft.ware.rest.modular.handover.service.IHandOverService;
/**
 * 交接班controller
 * @author paulo
 * @Date 2019-03-20 11:46
 */
@RestController
@RequestMapping("/user")
public class HandoverController extends BaseController{
	@Autowired
	private IHandOverService iHandOverService;
	/**
	 * 通过owner字段获取交接班当前详细信息
	 * @param owner
	 * @return
	 */
	@RequestMapping("handover")
	public Object getHandover(String owner){
		if(ToolUtil.isEmpty(owner)){
			throw new PauException(BizExceptionEnum.PARAME_ERROR);
		}
		HandOver ho = new HandOver();
		ho.setOwner(owner);
		return iHandOverService.selectOne(new EntityWrapper<>(ho));
	}
	
	

}
