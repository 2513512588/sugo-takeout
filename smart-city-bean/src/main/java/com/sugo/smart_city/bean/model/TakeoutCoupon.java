package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 外卖商家优惠卷表
 * @TableName takeout_coupon
 */
@TableName(value ="takeout_coupon")
@Data
public class TakeoutCoupon implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 外卖商家id
     */
    private Integer sellerId;

    /**
     * 优惠卷金额
     */
    private BigDecimal price;

    /**
     * 优惠卷类型 1 关注店铺 2 消费满多少
     */
    private Integer type;

    /**
     * 发行数量
     */
    @JsonIgnore
    private Integer quantity;

    /**
     * 条件金额
     */
    @TableField("`condition`")
    private BigDecimal condition;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;

    /**
     * 有效时长
     */
    private Long effectiveDuration;


}