package com.sugo.takeout.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sugo.takeout.bean.model.TakeoutActivity;
import com.sugo.takeout.bean.model.TakeoutCoupon;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * 商家详情信息
 * @author hehaoyang
 */
@Data
public class SellerDetailDto {

    /**
     * 主键id
     */
    @ApiModelProperty("卖家id")
    private Integer id;

    /**
     * 店铺名称
     */
    @ApiModelProperty("店铺名称")
    private String name;
    /**
     * 店铺招牌背景
     */
    @ApiModelProperty("店铺招牌背景")
    private String cover;

    /**
     * 店铺介绍
     */
    @ApiModelProperty("店铺介绍")
    private String description;
    /**
     * 店铺logo
     */
    @ApiModelProperty("店铺logo")
    private String avatar;
    /**
     * 店铺联系电话
     */
    @ApiModelProperty("店铺联系电话")
    private String phone;

    /**
     * 备用联系电话
     */
    @ApiModelProperty("备用联系电话")
    private String phoneAdditional;
    /**
     * 工商备案名称
     */
    @ApiModelProperty("工商备案名称")
    private String enterpriseName;

    /**
     * 工商备案地址
     */
    @ApiModelProperty("工商备案地址")
    private String enterpriseAddress;

    /**
     * 营业执照照片
     */
    @ApiModelProperty("营业执照照片")
    private String enterprisePic;
    /**
     * 开始配送时间
     */
    @ApiModelProperty("开始配送时间")
    private LocalTime deliveryTimeStart;

    /**
     * 结束配送时间
     */
    @ApiModelProperty("结束配送时间")
    private LocalTime deliveryTimeEnd;

    /**
     * 起送金额
     */
    @ApiModelProperty("起送金额")
    private Double minDeliveryPrice;
    /**
     * 店铺公告
     */
    @ApiModelProperty("店铺公告")
    private String notice;

    /**
     * 店铺评价数量
     */
    private Integer commentNum;

    /**
     * 店铺活动
     */
    private List<TakeoutActivity> activities;

    /**
     * 店铺优惠卷
     */
    private List<TakeoutCoupon> coupons;

    /**
     * 店铺评分
     */
    private Double score;

    /**
     * 月订单数量
     */
    private Integer monthOrderNum;

    /**
     * 配送时间
     */
    private Double avgDeliveryTime;

    /**
     * 商家位置
     */
    @JsonIgnore
    private String location;

    /**
     * 配送费
     */
    private Double deliveryFee;



}
