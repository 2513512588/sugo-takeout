package com.sugo.takeout.bean.enums;

import lombok.Getter;

@Getter
public enum TakeoutSellerStatus {

    /**
     * 审核中
     */
    UNDER_REVIEW(1, "店铺正在审核中"),
    /**
     * 封禁
     */
    FORBIDDEN(2, "您的账户已被封禁，请联系在线客服"),
    /**
     * 正常
     */
    NORMAL(3, "您的账户已正常开通店铺");

    private final int status;
    private final String message;

    TakeoutSellerStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
