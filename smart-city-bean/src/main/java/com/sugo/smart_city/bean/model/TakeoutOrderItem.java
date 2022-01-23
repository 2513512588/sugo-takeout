package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 外卖订单明细表
 * @TableName takeout_order_item
 */
@TableName(value ="takeout_order_item")
@Data
public class TakeoutOrderItem implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号
     */
    private String orderCode;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 单品总价
     */
    private Double total;

    /**
     * 商品规格名称组 json字符串
     */
    private String skuNameGroup;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}