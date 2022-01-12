package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 外卖优惠卷表
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
     * 店铺id
     */
    @JsonIgnore
    private Integer sellerId;

    /**
     * 满足条件
     */
    @TableField("`condition`")
    private BigDecimal condition;

    /**
     * 减免金额
     */
    private BigDecimal reduce;

    /**
     * 发行数量
     */
    private Integer quantity;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}