package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 外卖订单详情表
 * @TableName takeout_order_detail
 */
@TableName(value ="takeout_order_detail")
@Data
public class TakeoutOrderDetail implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 1 退款中 2 骑手反馈/报备
     */
    private Integer status;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}