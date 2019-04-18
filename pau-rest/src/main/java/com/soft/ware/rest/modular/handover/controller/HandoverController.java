package com.soft.ware.rest.modular.handover.controller;

import com.soft.ware.core.base.controller.BaseController;
import com.soft.ware.core.base.tips.ErrorTip;
import com.soft.ware.core.base.tips.SuccessTip;
import com.soft.ware.core.base.tips.Tip;
import com.soft.ware.rest.modular.auth.controller.dto.HandoverParam;
import com.soft.ware.rest.modular.auth.controller.dto.SessionUser;
import com.soft.ware.rest.modular.handover.model.THandoverRecord;
import com.soft.ware.rest.modular.handover.service.ITHandoverRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * @param sessionUser 当前登录的商户
	 * @return
	 */
	@RequestMapping("handover/get-handover")
	public Tip getHandover(@RequestParam Map<String ,Object> param,SessionUser sessionUser){
		param.put("owner_id",sessionUser.getOwnerId());
		List<THandoverRecord> handoverRecordList = itHandoverRecordService.getHandOver(param);
		if(handoverRecordList.size() > 0){
			return new SuccessTip(handoverRecordList);
		}
		return new ErrorTip();

	}


	/**
	 * 交接班,记录
	 * @return
	 */
	@RequestMapping(value = "handover",method = RequestMethod.POST)
	public Object handover(SessionUser user, HandoverParam param){
		/*HandOver over = overService.over(user, param);
		return render(over != null);*/
		return null;
	}
	
	

}
