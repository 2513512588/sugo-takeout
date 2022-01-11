package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sugo.smart_city.common.valid.Groups;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * 外卖商家表
 * @TableName takeout_seller
 */
@TableName(value ="takeout_seller")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeoutSeller implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty("卖家id")
    @TableId(type = IdType.AUTO)
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
     * 商家所在市
     */
    @ApiModelProperty("商家所在市")
    @NotBlank(message = "当前位置城市不能为空", groups = Groups.Query.class)
    private String city;

    /**
     * 商家所在省份
     */
    @ApiModelProperty("商家所在省份")
    @NotBlank(message = "当前位置省份不能为空", groups = Groups.Query.class)
    private String province;

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
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;

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

    /**
     * 店铺位置信息经纬度json字符串
     */
    @ApiModelProperty("店铺位置信息经纬度json字符串")
    private String location;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}