package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外卖商品表
 * @TableName takeout_goods
 */
@TableName(value ="takeout_goods")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeoutGoods implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品分类id
     */
    private Integer categoryId;

    /**
     * 商铺id
     */
    private Integer sellerId;

    /**
     * 商品图片
     */
    private String cover;

    /**
     * 商品销量
     */
    private Integer salesVolume;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 是否上架 0 未上架 1已上架
     */
    private Integer status;

    /**
     * 是否删除
     */
    @TableField(select = false)
    @JsonIgnore
    private Boolean isDeleted;

    /**
     * 是否为必点品 0 不是 1是
     */
    private Boolean isMandatory;

    /**
     * 起购数量
     */
    private Integer minPurchaseNum;

    /**
     * 每份打包费
     */
    private Double packingFee;

    /**
     * 商品原价
     */
    private Double originPrice;

    /**
     * 限购数量
     */
    private Integer maxPurchaseNum;

    /**
     * 库存数量
     */
    private Integer stock;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}