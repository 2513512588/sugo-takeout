package com.sugo.takeout.bean.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 卖家 商品 附加信息
 */
@Data
public class TakeoutSellerAdditionalDto implements Serializable {

    /**
     * 卖家距离你的距离
     */
    private Long distance;

    /**
     * 人均消费
     */
    private Double avgCost;

    /**
     * 月订单数量
     */
    private Integer monthOrderNum;


    /**
     * 评分
     */
    private Double avgScore;

    /**
     * 配送时间
     */
    private Long avgDeliveryTime;
}
