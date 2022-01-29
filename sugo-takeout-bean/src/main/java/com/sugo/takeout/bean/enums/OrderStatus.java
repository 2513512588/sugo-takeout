package com.sugo.takeout.bean.enums;

import lombok.Getter;

/**
 * @author hehaoyang
 */
@Getter
public enum OrderStatus {

    /**
     * 订单状态
     */
    TO_BE_PAID(1, "待支付"),
    PAID(2, "已支付"),
    ORDER_COMPLETION(3, "完成"),
    REFUND_CANCELLATION(4, "退款取消"),
    REFUND_IN_PROGRESS(5, "退款中"),
    EVALUATED(6, "已评价"),
    ORDER_TIMEOUT_CANCELLATION(7, "订单超时取消");


    private final int status;
    private final String message;

    OrderStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
