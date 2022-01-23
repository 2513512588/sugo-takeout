package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 外卖订单表
 * @TableName takeout_order
 */
@TableName(value ="takeout_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private String consignee;

    /**
     * 收货手机号
     */
    private String phone;

    /**
     * 收货人性别
     */
    private Integer consigneeSex;

    /**
     * 收货地址名称
     */
    private String addrName;

    /**
     * 收货详细地址
     */
    private String addrDetail;

    /**
     * 收货地址门牌号
     */
    private String addrHouseNumber;

    /**
     * 收货地址纬度
     */
    private String addrLat;

    /**
     * 收货地址经度
     */
    private String addrLng;

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
    private Double total;

    /**
     * 配送费
     */
    private Double deliveryFee;

    /**
     * 打包费
     */
    private Double packingFee;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    /**
     * 订单创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 订单状态 1 待支付 2 已支付  3 完成 4 取消 5 退款中
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

    /**
     * 餐具数量
     */
    private String tablewareNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}