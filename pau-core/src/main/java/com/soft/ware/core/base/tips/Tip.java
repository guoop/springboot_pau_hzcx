package com.soft.ware.core.base.tips;

/**
 * 返回给前台的提示（最终转化为json形式）
 */
public abstract class Tip {
	
	protected Object data;
    protected String code;
    protected String msg;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
