package com.sugo.smart_city.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@ApiModel("卖家更新资料vo对象")
public class TakeoutSellerUpdateVo implements Serializable {

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
     * 店铺介绍
     */
    @ApiModelProperty("店铺介绍")
    private String description;

    /**
     * 店铺公告
     */
    @ApiModelProperty("店铺公告")
    private String notice;

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
    private Double startDelivery;

}
