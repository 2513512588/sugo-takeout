package com.sugo.takeout.bean.enums;

import lombok.Getter;

@Getter
public enum GoodsStatus {

    /**
     * 未上架
     */
    NOT_ON_SHELF(1, "商品暂未上架"),
    /**
     * 已上架
     */
    ON_SHELF(2, "商品已上架"),
    /**
     * 售罄
     */
    SELL_OUT(3, "商品已售罄");

    private final int status;
    private final String message;

    GoodsStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
