package com.sugo.takeout.bean.dto;

import lombok.Data;

import java.time.LocalTime;

/**
 * @author hehaoyang
 */
@Data
public class AcceptedRiderOrderDto {

    /**
     * 订单编号
     */
    private String code;

    /**
     * 起点卖家店铺名称
     */
    private String shopName;

    /**
     * 卖家地址详情
     */
    private String sellerAddrDesc;

    /**
     * 买家地址名称
     */
    private String consigneeAddrName;

    /**
     * 买家地址详情
     */
    private String consigneeAddrDesc;

    /**
     * 买家地址门牌号
     */
    private String consigneeAddrHouseNumber;

    /**
     * 系统指定送达时间
     */
    private LocalTime arriveTime;

    /**
     * 配送费
     */
    private Double price;

    /**
     * 卖家位置经纬度
     */
    private String sellerLatLng;

    /**
     * 买家位置经纬度
     */
    private String consigneeLatLng;


    /**
     * 卖家手机号
     */
    private String sellerPhone;

    /**
     * 买家姓名
     */
    private String consigneeName;

    /**
     * 买家性别
     */
    private Integer consigneeSex;

    /**
     * 买家手机号
     */
    private String consigneePhone;

}
