package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外卖订单打赏表
 * @TableName takeout_reward
 */
@TableName(value ="takeout_reward")
@Data
public class TakeoutReward implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 打赏备注
     */
    private String remark;

    /**
     * 打赏金额
     */
    private BigDecimal price;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 骑手id
     */
    private Integer riderId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}