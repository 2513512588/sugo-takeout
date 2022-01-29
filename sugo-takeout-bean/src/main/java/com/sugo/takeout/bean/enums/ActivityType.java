package com.sugo.takeout.bean.enums;

import lombok.Getter;

@Getter
public enum ActivityType {

    /**
     * 活动类型 1 红包减免 2 折扣
     */
    REDUCTION(1, "红包减免"),
    DISCOUNT(2, "折扣");

    private final int type;
    private final String message;

    ActivityType(int type, String message) {
        this.type = type;
        this.message = message;
    }

}
