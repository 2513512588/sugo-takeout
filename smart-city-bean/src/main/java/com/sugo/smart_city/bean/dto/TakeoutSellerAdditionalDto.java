package com.sugo.smart_city.bean.dto;

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
     * 已售数量
     */
    private Integer monthSoldNum;


    /**
     * 评分
     */
    private Double avgScore;
}
