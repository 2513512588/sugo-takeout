package com.sugo.takeout.rider.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hehaoyang
 */
@Data
public class TakeoutOrderDto implements Serializable {

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
     * 起点经纬度
     */
    private String originLatLng;

    /**
     * 目标地址名称
     */
    private String targetAddrName;

    /**
     * 目标地址经纬度
     */
    private String targetLatLng;

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
