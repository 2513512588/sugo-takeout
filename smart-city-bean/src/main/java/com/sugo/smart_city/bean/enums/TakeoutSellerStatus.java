package com.sugo.smart_city.bean.enums;

import lombok.Getter;

@Getter
public enum TakeoutSellerStatus {

    /**
     * 审核中
     */
    UNDER_REVIEW(1),
    /**
     * 封禁
     */
    FORBIDDEN(2),
    /**
     * 正常
     */
    NORMAL(3);

    private final Integer status;

    TakeoutSellerStatus(Integer status) {
        this.status = status;
    }
}
