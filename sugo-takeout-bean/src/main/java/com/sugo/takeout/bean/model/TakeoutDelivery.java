package com.sugo.takeout.bean.model;

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
 * 外卖订单配送表
 * @TableName takeout_delivery
 */
@TableName(value ="takeout_delivery")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TakeoutDelivery implements Serializable {
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
     * 骑手id
     */
    private Integer riderId;

    /**
     * 商家id
     */
    private Integer sellerId;

    /**
     * 订单状态 1 骑手已接单 2 商家已出餐 3骑手已取餐 4 已送达
     */
    private Integer status;

    /**
     * 更新时间
     */
    private LocalDateTime gmtUpdate;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}