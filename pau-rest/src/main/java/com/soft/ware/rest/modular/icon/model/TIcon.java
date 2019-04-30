package com.soft.ware.rest.modular.icon.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
/**
  * 图标
*/
@TableName("t_icon")
public class TIcon extends Model<TIcon> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    //图片名称
    private String name;
    //图类型（1:商品2食品3其他）
    private Integer type;
    //图地址
    private String cover;


    public String getId(){
        return id;
    }

    public TIcon setId(String id){
        this.id = id;
        return this;
    }


    public String getName(){
        return name;
    }

    public TIcon setName( String name){
        this.name = name;
        return this;
    }
    public Integer getType(){
        return type;
    }

    public TIcon setType( Integer type){
        this.type = type;
        return this;
    }
    public String getCover(){
        return cover;
    }

    public TIcon setCover( String cover){
        this.cover = cover;
        return this;
    }



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "TIcon{" +
        "id=" + id +
            ", name=" + name +
            ", type=" + type +
            ", cover=" + cover +
        "}";
    }

}