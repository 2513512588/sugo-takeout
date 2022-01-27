package com.sugo.takeout.rider.dto;

import lombok.Data;

@Data
public class TakeoutOrderDto {

    /**
     * 订单编号
     */
    private String code;

    /**
     * 起点地址名称
     */
    private String originAddrName;

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
    private String [] originLatLng;

    /**
     * 目标地址名称
     */
    private String targetAddrName;

    /**
     * 目标地址详情
     */
    private String targetAddrDesc;

    /**
     * 目标地址门牌号
     */
    private String addrHouseNumber;

    /**
     * 目标地址经纬度
     */
    private String [] targetLatLng;

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
