package com.soft.ware.rest.modular.auth.util;

public class ParamUtils {

    /**
     * 订单状态的封装
     */

    public static int gevoidStatus(String status){
        switch (status){
            case "deliver":
                return 1;
            case "confirm":
                return 1;
            case "confirmed":
                return 10;
            case "delivering":
                return 2;
            case "done":
                return 3;
            case "cancel":
                return -1;

        }
        return 0;
    }
}
