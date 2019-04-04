package com.soft.ware.core.base.tips;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回给前台的错误提示
 */
public class ErrorTip extends Tip {

    public ErrorTip(int code,String message) {
        super();
        this.code = code;
        this.message =message;
    }
    
    public Map<String,Object> resultError(int code,String message){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", message);
		return map;
	}
    
}
