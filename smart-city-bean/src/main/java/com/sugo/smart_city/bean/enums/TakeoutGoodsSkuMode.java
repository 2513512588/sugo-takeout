package com.sugo.smart_city.bean.enums;

import lombok.Getter;

@Getter
public enum TakeoutGoodsSkuMode {

    /**
     * 1 独立价格 2 加价
     */
    INDEPENDENT_PRICE(1, "独立价格"),
    MARK_UP_PRICE(1, "加价");

    private final int mode;
    private final String message;

    TakeoutGoodsSkuMode(int mode, String message) {
        this.mode = mode;
        this.message = message;
    }
}
