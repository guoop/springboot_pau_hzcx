package com.soft.ware.rest.modular.auth.util;

import com.soft.ware.rest.common.persistence.model.TblOwner;
import com.soft.ware.rest.modular.auth.controller.dto.ImGroupType;
import com.soft.ware.rest.modular.owner.model.TOwner;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamUtils {

    /**
     * 订单状态的封装
     */

    public static int getOrderStatus(String status){
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
            case "refund":
                return -6;

        }
        return 0;
    }
    public static List<Map<String,Object>> getAllFunction(String arg[]){
        List<Map<String,Object>> listMap = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < arg.length; i++) {
            if(arg[i].equals("configOrderPhone")){
                map.put("key","configOrderPhone");
                map.put("title","订单通知");
                listMap.add(map);
            }
            if(arg[i].equals("configStaff")){
                map.put("key","configStaff");
                map.put("title","店员管理");
                listMap.add(map);
            }
            if(arg[i].equals("configShop")){
                map.put("key","configShop");
                map.put("title","商户信息");
                listMap.add(map);
            }
            if(arg[i].equals("configDelivery")){
                map.put("key","configDelivery");
                map.put("title","配送设置");
                listMap.add(map);
            }
            if(arg[i].equals("configGoods")){
                map.put("key","configGoods");
                map.put("title","商品默认值设置");
                listMap.add(map);
            }
            if(arg[i].equals("doRefund")){
                map.put("key","doRefund");
                map.put("title","退款");
                listMap.add(map);
            }
            if(arg[i].equals("goodsMan")){
                map.put("key","goodsMan");
                map.put("title","商品管理");
                listMap.add(map);
            }
            if(arg[i].equals("goodsManStorage")){
                map.put("key","goodsManStorage");
                map.put("title","进货入库");
                listMap.add(map);
            }
            if(arg[i].equals("goodsManPrice")){
                map.put("key","goodsManPrice");
                map.put("title","商品调价");
                listMap.add(map);
            }
            if(arg[i].equals("categoryMan")){
                map.put("key","categoryMan");
                map.put("title","分类管理");
                listMap.add(map);
            }
            if(arg[i].equals("printPriceTicket")){
                map.put("key","printPriceTicket");
                map.put("title","打印价签");
                listMap.add(map);
            }
            if(arg[i].equals("configOwner")){
                map.put("key","configOwner");
                map.put("title","设置");
            }
        }
        return listMap;
    }


    public static String buildImUserName(TOwnerStaff staff, ImGroupType type){
        return staff.getOwnerId() + "-" + type.getSeparator() + "-" + staff.getPhone();
    }

    /**
     * 统一群组管理员命名规则
     * @param owner
     * @param type
     * @return
     */
    public static String buildOwnerGroupUsername(TOwner owner, ImGroupType type){
        return owner.getId() + "--" + type.name();
    }
}
