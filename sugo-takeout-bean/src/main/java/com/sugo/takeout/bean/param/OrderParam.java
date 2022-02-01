package com.sugo.takeout.bean.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class OrderParam {

    /**
     * 收货地址id
     */
    @NotNull(message = "收货地址id不能为空")
    private Integer addrId;

    /**
     * 店铺id
     */
    @NotNull(message = "店铺id不能为空")
    private Integer sellerId;

    /**
     * 订单备注
     */
    @Length(max = 50, message = "订单备注字符数量不能超过20")
    private String remark;

    /**
     * 餐具数量
     */
    @Length(max = 20, message = "餐具数量字符数量不能超过20")
    private String tablewareNum;

    /**
     * 优惠卷id
     */
    private Integer couponId;


}
