package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 外卖商家类型表
 * @TableName takeout_seller_type
 */
@TableName(value ="takeout_seller_type")
@Data
public class TakeoutSellerType implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 店铺分类名称
     */
    private String name;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 是否删除 0 未删除 1已删除
     */
    @TableField(select = false)
    @JsonIgnore
    private Boolean isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}