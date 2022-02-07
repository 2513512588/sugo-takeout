package com.sugo.takeout.bean.dto;

import com.sugo.takeout.bean.vo.BasketGoodsItemVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author hehaoyang
 */
@Data
public class OrderDetailDto {

    /**
     * 订单编号
     */
    private String code;

    /**
     * 订单项
     */
    private List<BasketGoodsItemVo> orderItemList;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 订单支付时间
     */
    private LocalDateTime payTime;

    /**
     * 店铺logo
     */
    private String sellerLogo;
    /**
     * 店铺名称
     */
    private String sellerName;

    /**
     * 店铺id
     */
    private Integer sellerId;

    /**
     * 店铺手机号
     */
    private String sellerPhone;

    /**
     * 订单总金额
     */
    private Double total;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 配送费
     */
    private Double deliveryFee;

    /**
     * 打包费
     */
    private Double packingFee;

    /**
     * 店铺红包减免
     */
    private Double couponDiscount;

    /**
     * 活动减免
     */
    private Double activityDiscount;

    /**
     * 收货人姓名
     */
    private String consignee;

    /**
     * 收货手机号
     */
    private String consigneePhone;

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
     * 订单备注
     */
    private String remark;

    /**
     * 接单骑手id
     */
    private Integer riderId;

    /**
     * 接单骑手姓名
     */
    private String riderName;

    /**
     * 餐具数量
     */
    private String tablewareNum;


    /**
     * 订单支付方式 1 电子钱包 2 支付宝 3 微信
     */
    private Integer paymentType;

    /**
     * 预计到达时间
     */
    private LocalTime arriveTime;
}
