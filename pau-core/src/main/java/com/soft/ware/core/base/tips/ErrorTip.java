package com.soft.ware.core.base.tips;

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
        this.msg = msg;
    }
    
}
