package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户收货地址表
 * @TableName takeout_address
 */
@TableName(value ="takeout_address")
@Data
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
    private String name;

    /**
     * 收货人手机号
     */
    @ApiModelProperty("收货人手机号")
    private String phone;

    /**
     * 收货省份
     */
    @ApiModelProperty("收货省份")
    private String province;

    /**
     * 收货市
     */
    @ApiModelProperty("收货市")
    private String city;

    /**
     * 收货县区
     */
    @ApiModelProperty("收货县区")
    private String county;

    /**
     * 收货详细地址
     */
    @ApiModelProperty("收货详细地址")
    private String address;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Integer userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}