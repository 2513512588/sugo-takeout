package com.sugo.takeout.rider.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hehaoyang
 * 骑手 订单列表（新订单） 数据模型
 */
@Data
public class NewRiderOrderDto implements Serializable {

    /**
     * 订单编号
     */
    private String code;

    /**
     * 起点店铺名称
     */
    private String shopName;

    /**
     * 起点地址详情
     */
    private String originAddrDesc;

    /**
     * 距离起点地址距离
     */
    private Long originDistance;

    /**
     * 目标地址名称
     */
    private String targetAddrName;

    /**
     * 距离目标地址距离
     */
    private Long targetDistance;

    /**
     * 送达时间 /秒
     */
    private Long timestamp;

    /**
     * 配送费
     */
    private Double price;

}
