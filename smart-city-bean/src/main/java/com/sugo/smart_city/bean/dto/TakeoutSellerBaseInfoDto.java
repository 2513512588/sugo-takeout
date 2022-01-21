package com.sugo.smart_city.bean.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sugo.smart_city.common.valid.Groups;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeoutSellerBaseInfoDto implements Serializable {
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
     * 店铺类型id
     */
    @ApiModelProperty("店铺类型id")
    private Integer typeId;

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
     * 店铺状态 1 审核中 2 封禁 3 正常
     */
    @ApiModelProperty("店铺状态 1 审核中 2 封禁 3 正常")
    private Integer status;

    /**
     * 店铺公告
     */
    @ApiModelProperty("店铺公告")
    private String notice;


}