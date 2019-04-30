package com.soft.ware.rest.modular.auth.controller.dto;

import com.soft.ware.core.util.DateUtil;
import com.soft.ware.core.util.ToolUtil;

import java.util.Date;

public class HandoverPageParam {

    private String start;
    private String end;

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Date getStart() {
        return ToolUtil.isEmpty(start) ? null : DateUtil.parse(start, "YYYYMMdd");
    }

    public Date getEnd() {
        return ToolUtil.isEmpty(end) ? null : DateUtil.parse(end, "YYYYMMdd");
    }

}
