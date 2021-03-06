package com.sugo.takeout.bean.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 商家列表推荐的商家数据模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerListDto implements Serializable {
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
     * 起送金额
     */
    @ApiModelProperty("起送金额")
    private Double startDelivery;

    /**
     * 店铺公告
     */
    @ApiModelProperty("店铺公告")
    private String notice;

    private SellerAdditionalDto additionalData = new SellerAdditionalDto();


}