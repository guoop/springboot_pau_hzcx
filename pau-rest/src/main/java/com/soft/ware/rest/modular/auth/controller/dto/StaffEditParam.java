package com.soft.ware.rest.modular.auth.controller.dto;


import com.google.common.collect.Lists;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
import com.soft.ware.rest.modular.owner_staff.model.TOwnerStaff;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Base64;
import java.util.List;

public class StaffEditParam {

    private String id;
    private String categoryList;
    private String functionList;
    private String password;
    @Pattern(regexp = "^1\\d{10}$",message = "手机号不合法")
    private String phone;
    private String urlList;
    private String name;
    private String createTime;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(String categoryList) {
        this.categoryList = categoryList;
    }

    public String getFunctionList() {
        return functionList;
    }

    public void setFunctionList(String functionlist) {
        this.functionList = functionlist;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl_list() {
        return urlList;
    }

    public void setUrl_list(String url_list) {
        this.urlList = urlList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public TOwnerStaff update(TOwnerStaff s){
        TOwnerStaff tOwnerStaff = new TOwnerStaff();
        StaffEditParam param = this;
        System.out.println(param.getName());
        tOwnerStaff.setName(ToolUtil.isNotEmpty(param.getName()) ? param.getName() :s.getName());

        if ("-1".equals(param.getPassword())) {
            //todo yancc 清除密码方式是否正确，
            // 清除店员的收银APP密码
            tOwnerStaff.setPassword(null);
            //password 不是设置为空 因该也行
            //sql += (', password = null ');
        }
        if (!ToolUtil.isEmpty(param.getPassword()) && !param.getPassword().equals("-1")) {
            // 修改店员的收银APP密码
            String password = "^a9682150f2e011e8uy572f1cf5acecff-" + param.getPhone() + "-" + param.getPassword() + "$";
            password = Base64.getEncoder().encodeToString(DigestUtils.updateDigest(DigestUtils.getMd5Digest(), password).digest());
            tOwnerStaff.setPassword(password);
        }

        tOwnerStaff.setStatus(Integer.valueOf(param.getStatus()));
        tOwnerStaff.setPhone(param.getPhone());
        tOwnerStaff.setFunctionList(param.getFunctionList());
        tOwnerStaff.setCategoryList(param.getCategoryList());
        tOwnerStaff.setUrlList(getUrls());
        return tOwnerStaff;
    }


    public static final Kv<String, String> kvs = Kv.init();

    static {
        kvs.set("configOrderPhone", "/info");
        kvs.set("configStaff","/staff/list,/staff/index,/staff/man,/staff/del");
        kvs.set("configShop","/info");
        kvs.set("configDelivery","/info");
        kvs.set("configGoods","/info");
        kvs.set("doRefund","/order/refund");
        kvs.set("goodsMan","/goods/update,/goods/edit,/goods/top,/goods/delete,/goods/addByHand,/goods/addByScan,/goods/repository");
        kvs.set("goodsManStorage","/goods/storage");
        kvs.set("goodsManPrice","/goods/price");
        kvs.set("categoryMan","/category/icons,/category/index,/category/sort,/category/del");
        kvs.set("printPriceTicket", "");
        kvs.set("configOwner","");
    }

    public String getUrls(){
        String ss = getFunctionList() == null ? "" : getFunctionList();
        String[] arr = ss.split(",");
        List<String> sb = Lists.newArrayList();
        for (String s : arr) {
            sb.add(kvs.get(s));
        }
        return "," + StringUtils.join(sb, ",").replaceAll(",,", ",").replaceAll("//", "");
    }


}
