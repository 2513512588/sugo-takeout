package com.sugo.takeout.bean.dto;

import lombok.Data;

/**
 * 骑手订单统计数据
 * @author hehaoyang
 */
@Data
public class RiderOrderDataDto {

    /**
     * 待取餐数量
     */
    private Integer numberOfMealsToBeTaken;
    /**
     * 配送中数量
     */
    private Integer numberOfInDistribution;

}
