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

/**
 * 外卖用户购物车表
 * @TableName takeout_basket
 */
@TableName(value ="takeout_basket")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutBasket implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 店铺id
     */
    private Integer sellerId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 加购数量
     */
    private Integer quantity;

    /**
     * 用户id
     */
    @JsonIgnore
    private Integer userId;

    /**
     * skuId组 json字符串
     */
    private String skuIdGroup;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}