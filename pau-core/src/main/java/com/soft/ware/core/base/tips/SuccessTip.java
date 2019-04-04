package com.soft.ware.core.base.tips;

import com.soft.ware.core.base.controller.BaseController;

/**
 * 返回给前台的成功提示
 */
public class SuccessTip extends Tip {
	
	public SuccessTip(){
		super.code = BaseController.SUCCESS;
		super.msg = "操作成功";
	}

	public SuccessTip(String msg){
		super.code = BaseController.SUCCESS;
		super.msg = msg;
	}
	
	
	


	
}
