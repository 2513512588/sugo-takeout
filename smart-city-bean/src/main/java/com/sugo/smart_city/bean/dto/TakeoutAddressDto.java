package com.sugo.smart_city.bean.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutAddressDto implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 收货人姓名
     */
    @ApiModelProperty("收货人姓名")
    private String consignee;

    /**
     * 收货人手机号
     */
    @ApiModelProperty("收货人手机号")
    private String phone;


    /**
     * 收货地址名称
     */
    @ApiModelProperty("收货地址名称")
    private String name;

    /**
     * 收货详细地址
     */
    @ApiModelProperty("收货详细地址")
    private String address;

    /**
     * 门牌号
     */
    @ApiModelProperty("门牌号")
    private String houseNumber;

    /**
     * 标记
     */
    @ApiModelProperty("收货详细地址")
    private String marker;


    /**
     * 性别 1 男 2女
     */
    @ApiModelProperty("性别 1 男 2女")
    private Integer sex;



}