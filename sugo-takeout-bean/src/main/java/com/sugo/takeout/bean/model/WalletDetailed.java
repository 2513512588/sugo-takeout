package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 电子钱包交易明细表
 * @TableName wallet_detailed
 */
@TableName(value ="wallet_detailed")
@Data
public class WalletDetailed implements Serializable {
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
     * 类型 1 收入 2 支出
     */
    private Boolean type;

    /**
     * 名称
     */
    private String remark;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}