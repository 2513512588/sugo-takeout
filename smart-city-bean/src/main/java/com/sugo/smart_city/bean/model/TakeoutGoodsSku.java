package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 外卖商品sku
 * @TableName takeout_goods_sku
 */
@TableName(value ="takeout_goods_sku")
@Data
public class TakeoutGoodsSku implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 型号名称
     */
    private String name;

    /**
     * 库存类型
     */
    @JsonIgnore
    private String type;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 单价
     */
    private Double originPrice;
    /**
     * 单价
     */
    private Double price;

    /**
     * 单位名称
     */
    private String unit;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}