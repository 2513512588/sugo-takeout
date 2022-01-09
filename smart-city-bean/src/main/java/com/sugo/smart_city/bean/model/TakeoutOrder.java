package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 外卖订单表
 * @TableName takeout_order
 */
@TableName(value ="takeout_order")
@Data
public class TakeoutOrder implements Serializable {
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
     * 商家id
     */
    private Integer sellerId;

    /**
     * 收货人姓名
     */
    private String fullName;

    /**
     * 收货手机号
     */
    private String phone;

    /**
     * 收货地址省份
     */
    private String province;

    /**
     * 收货地址市
     */
    private String city;

    /**
     * 收货地址县
     */
    private String county;

    /**
     * 收货详细地址
     */
    private String address;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 预计送达时间
     */
    private LocalDateTime arriveTime;

    /**
     * 商品订单总价
     */
    private BigDecimal total;

    /**
     * 配送费
     */
    private BigDecimal deliveryFee;

    /**
     * 打包费
     */
    private BigDecimal packingFee;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 订单创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 订单状态 1 已支付 2 商家已出餐 3 骑手已取餐 4 已送达 5 完成 6 退款中 7 取消 8 已评价
     */
    private Integer status;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 接单骑手id
     */
    private Integer riderId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}