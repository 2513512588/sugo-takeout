package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 外卖商家优惠卷表
 * @TableName takeout_coupon
 */
@TableName(value ="takeout_coupon")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private Double price;

    /**
     * 是否关注店铺才能领取
     */
    private Boolean conditionIsFav;

    /**
     * 消费多少金额可以领取
     */
    private Double conditionCostPrice;

    /**
     * 发行数量
     */
    @JsonIgnore
    private Integer quantity;

    /**
     * 使用条件金额
     */
    @TableField("`condition`")
    private Double condition;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;

    /**
     * 有效时长(秒)
     */
    private Long effectiveDuration;


}