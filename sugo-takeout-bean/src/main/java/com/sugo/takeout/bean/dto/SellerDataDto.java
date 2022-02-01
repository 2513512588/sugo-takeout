package com.sugo.takeout.bean.dto;

import lombok.Data;

/**
 * 卖家统计数据
 * @author hehaoyang
 */
@Data
public class SellerDataDto {

    /**
     * 店铺id
     */
    private Integer sellerId;

    /**
     * 店铺logo
     */
    private String logo;

    /**
     * 店铺名称
     */
    private String sellerName;

    /**
     * 待支付数量
     */
    private Integer quantityToBePaid;
    /**
     * 待出餐数量
     */
    private Integer numberOfMealsToBeServed;
    /**
     * 退款售后数量
     */
    private Integer refundQuantity;
    /**
     * 综合评分
     */
    private Double score;
    /**
     * 支付金额
     */
    private Double amount;
    /**
     * 支付人数
     */
    private Integer numberOfPayers;
    /**
     * 支付订单数量
     */
    private Integer numberOfPaymentOrders;
    /**
     * 支付客单价
     */
    private Double customerUnitPrice;
    /**
     * 昨日预计收入
     */
    private Double estimatedIncome;
    /**
     * 昨日有效订单
     */
    private Integer effectiveOrderQuantity;
    /**
     * 昨日访客数
     */
    private Integer numberOfVisitors;

}
