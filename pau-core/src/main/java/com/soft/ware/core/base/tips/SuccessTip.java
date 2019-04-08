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

	/**
	 * 封装成功的信息
	 * @param msg
	 */
	public SuccessTip(String msg){
		super.code = BaseController.SUCCESS;
		super.msg = msg;
	}

	/**
	 *  封装成功的数据
	 * @param data
	 */
	public SuccessTip(Object data){
		super.code = BaseController.SUCCESS;
		super.msg = "操作成功" ;
        super.data = data;
	}
	
	
	


	
}
