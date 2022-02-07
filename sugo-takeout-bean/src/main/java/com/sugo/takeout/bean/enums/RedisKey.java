package com.sugo.takeout.bean.enums;

import lombok.Getter;

@Getter
public enum RedisKey {

    /**
     * 骑手位置
     */
    RIDER_LOCATION("rider-location", "骑手位置"),
    RIDER_ORDER("rider-order", "骑手订单");

    private final String name;
    private final String desc;

    RedisKey(String key, String desc) {
        this.name = key;
        this.desc = desc;
    }
}
