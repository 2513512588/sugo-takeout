package com.sugo.takeout.bean.enums;

import lombok.Getter;

@Getter
public enum DeliveryStatus {

    /**
     * 等待商家出餐
     */
    WAITING_FOR_MEALS(1, "等待商家出餐"),
    /**
     * 商家已出餐
     */
    MEALS_HAVE_BEEN_SERVED(2, "商家已出餐"),

    /**
     * 骑手已接单
     */
    WAITING_FOR_RIDER_RECEIVE_ORDER(1, "等待骑手接单"),
    /**
     * 骑手已接单
     */
    RECEIVED_ORDER(2, "骑手已接单"),
    /**
     * 骑手已取餐
     */
    MEAL_TAKEN(3, "骑手已取餐"),
    /**
     * 已送达
     */
    DELIVERED(4, "已送达");

    private final int status;
    private final String message;

    DeliveryStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
