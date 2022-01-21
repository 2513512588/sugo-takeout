package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 外卖优惠券领取记录表
 * @TableName takeout_coupon_receive
 */
@TableName(value ="takeout_coupon_receive")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutCouponReceive implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 优惠卷id
     */
    private Integer couponId;

    /**
     * 状态 1 未使用 2 已使用
     */
    private Integer status;

    /**
     * 过期时间
     */
    private LocalDateTime expirationTime;

    /**
     * 领卷时间
     */
    private LocalDateTime gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}