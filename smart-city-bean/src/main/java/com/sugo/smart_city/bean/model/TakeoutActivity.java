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
import java.time.LocalDateTime;

/**
 * 外卖商家活动表
 * @TableName takeout_coupon
 */
@TableName(value ="takeout_activity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutActivity implements Serializable {
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
     * 活动类型 1 红包减免 2 折扣
     */
    private Integer type;

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
    @JsonIgnore
    private Integer quantity;

    /**
     * 活动开始时间
     */
    private LocalDateTime startTime;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}