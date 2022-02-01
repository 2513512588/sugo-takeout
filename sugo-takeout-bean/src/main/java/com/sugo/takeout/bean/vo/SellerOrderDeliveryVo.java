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
     * 订单状态 1 骑手已接单 2 商家已出餐 3骑手已取餐 4 已送达
     */
    private Integer status;
    /**
     * 更新时间
     */
    private LocalDateTime currentTime;
}
