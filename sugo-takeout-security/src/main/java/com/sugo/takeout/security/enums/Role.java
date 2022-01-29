package com.sugo.takeout.security.enums;

import lombok.Getter;

/**
 * 角色枚举
 */
@Getter
public enum Role {

    /**
     * 基础用户
     */
    ROLE_USER(1, "ROLE_USER", "基础用户"),
    /**
     * 外卖商家
     */
    ROLE_TAKEOUT_SELLER(2, "ROLE_TOKEOUT_SELLER", "外卖商家"),
    /**
     * 外卖骑手
     */
    ROLE_TAKEOUT_RIDER(3, "ROLE_TOKEOUT_RIDER", "外卖骑手");

    private final Integer id;
    private final String name;
    private final String remark;

    Role(Integer id, String name, String remark) {
        this.id = id;
        this.name = name;
        this.remark = remark;
    }

    public static Role parse(Integer roleId){
        if (ROLE_USER.getId().equals(roleId)){
            return ROLE_USER;
        }else if (ROLE_TAKEOUT_SELLER.getId().equals(roleId)){
            return ROLE_TAKEOUT_SELLER;
        }else if (ROLE_TAKEOUT_RIDER.getId().equals(roleId)){
            return ROLE_TAKEOUT_RIDER;
        }
        return null;
    }

}
