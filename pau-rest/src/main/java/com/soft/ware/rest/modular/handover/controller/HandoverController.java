package com.soft.ware.rest.modular.handover.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverPageParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.auth.util.Page;
import com.soft.ware.rest.modular.handover.service.ITHandoverRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 交接班controller
 * @author paulo
 * @Date 2019-03-20 11:46
 */
@RestController
@RequestMapping("/owner/v1")
public class HandoverController extends BaseController{
	@Autowired
	private ITHandoverRecordService itHandoverRecordService;
	/**
	 * 通过owner字段获取交接班当前详细信息
	 * @param param startTime开始时间，endTime结束时间，page当前页，size当前查询的页面数量
	 * @param user 当前登录的商户
	 * @return
	 */
	@RequestMapping("handover/getHandover")
	public Tip getHandover(HandoverPageParam param, Page page, SessionUser user){
		List<Map<String, Object>> list = itHandoverRecordService.findPage(user,param,page);
		return render(list);
	}


	

}
