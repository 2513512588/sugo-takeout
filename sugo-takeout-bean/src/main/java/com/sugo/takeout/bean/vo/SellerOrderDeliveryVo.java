package com.sugo.takeout.bean.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SellerOrderDeliveryVo {

    /**
     * 骑手id
     */
    private Integer riderId;
    /**
     * 骑手姓名
     */
    private String riderName;
    /**
     * 骑手手机号
     */
    private String riderPhone;
    /**
     * 商家状态 1 商家待出餐 2 商家已出餐
     */
    private Integer sellerStatus;

    /**
     * 骑手状态 1 待骑手接单 2 骑手已接单 3 骑手已取餐 4 已送达
     */
    private Integer riderStatus;
    /**
     * 更新时间
     */
    private LocalDateTime currentTime;
}
