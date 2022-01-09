package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 外卖骑手评价表
 * @TableName takeout_rider_evaluate
 */
@TableName(value ="takeout_rider_evaluate")
@Data
public class TakeoutRiderEvaluate implements Serializable {
    /**
     * 主键id
     */
    @TableId
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderItemId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 骑手id
     */
    private Integer riderId;

    /**
     * 评分
     */
    private BigDecimal score;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价时间
     */
    private LocalDateTime gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}