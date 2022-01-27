package com.sugo.takeout.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户收货地址表
 * @TableName takeout_address
 */
@TableName(value ="takeout_address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutAddress implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
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
    @ApiModelProperty("标记")
    private String marker;


    /**
     * 性别 1 男 2女
     */
    @ApiModelProperty("性别 1 男 2女")
    private Integer sex;

    /**
     * 用户id
     */
    @JsonIgnore
    @ApiModelProperty("用户id")
    private Integer userId;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    private String lat;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private String lng;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}