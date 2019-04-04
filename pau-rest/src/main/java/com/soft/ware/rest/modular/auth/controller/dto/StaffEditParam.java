package com.soft.ware.rest.modular.auth.controller.dto;


import com.google.common.collect.Lists;
import com.soft.ware.core.util.Kv;
import com.soft.ware.core.util.ToolUtil;
import com.soft.ware.rest.common.persistence.model.TblOwnerStaff;
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
    private String owner;
    private String category_list;
    private String function_list;
    private String password;
    @Pattern(regexp = "^1\\d{10}$",message = "手机号不合法")
    private String phone;
    private String url_list;
    private String name;
    private String created_at;
    @NotNull
    @Min(value = 0)
    @Max(value = 10)
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCategory_list() {
        return category_list;
    }

    public void setCategory_list(String category_list) {
        this.category_list = category_list;
    }

    public String getFunction_list() {
        return function_list;
    }

    public void setFunction_list(String function_list) {
        this.function_list = function_list;
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
        return url_list;
    }

    public void setUrl_list(String url_list) {
        this.url_list = url_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public StaffEditParam update(TblOwnerStaff s){
        StaffEditParam param = this;
        s.setName(ToolUtil.isEmpty(param.getName()) ? s.getName() : param.getName());

        if ("-1".equals(param.getPassword())) {
            //todo yancc 清除密码方式是否正确，
            // 清除店员的收银APP密码
            s.setPassword(null);
            //password 不是设置为空 因该也行
            //sql += (', password = null ');
        }
        if (!ToolUtil.isEmpty(param.getPassword()) && !param.getPassword().equals("-1")) {
            // 修改店员的收银APP密码
            String password = "^a9682150f2e011e8uy572f1cf5acecff-" + param.getPhone() + "-" + param.getPassword() + "$";
            password = Base64.getEncoder().encodeToString(DigestUtils.updateDigest(DigestUtils.getMd5Digest(), password).digest());
            s.setPassword(password);
        }

        s.setStatus(param.getStatus());
        s.setPhone(param.getPhone());
        s.setFunctionList(param.getFunction_list());
        s.setCategoryList(param.getCategory_list());
        s.setUrlList(getUrls());
        return this;
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
    }

    public String getUrls(){
        String ss = getFunction_list() == null ? "" : getFunction_list();
        String[] arr = ss.split(",");
        List<String> sb = Lists.newArrayList();
        for (String s : arr) {
            sb.add(kvs.get(s));
        }
        return "," + StringUtils.join(sb, ",").replaceAll(",,", ",").replaceAll("//", "");
    }


}
