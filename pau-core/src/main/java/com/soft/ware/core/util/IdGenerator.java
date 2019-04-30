package com.soft.ware.core.util;

import com.baomidou.mybatisplus.toolkit.IdWorker;

/**
 * 唯一id生成器
 */
public class IdGenerator {

    public static long getIdLong() {
        return IdWorker.getId();
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
    /**
     * 全局项目id
     * @return
     */
    public static String getId() {
        return  getOrderNo()+DateUtil.timestampToDate()+ToolUtil.getRandomString(6);
    }
    /**
     * 下单生成的订单编号
     * @return
     */
    public static String getOrderNo(){
        return String.valueOf(IdWorker.getId());
    }

}
