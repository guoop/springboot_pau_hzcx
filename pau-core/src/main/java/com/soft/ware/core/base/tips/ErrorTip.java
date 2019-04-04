package com.soft.ware.core.base.tips;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回给前台的错误提示
 */
public class ErrorTip extends Tip {

    public ErrorTip() {
        this(500, "操作失败");
    }

    public ErrorTip(String msg) {
        this(500, msg);
    }

    public ErrorTip(int code,String msg) {
        this(code + "", msg);
    }

    public ErrorTip(String code,String msg) {
        super();
        this.code = code + "";
    }
    
    public Map<String,Object> resultError(int code,String message){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("message", message);
		return map;
	}
    
}
