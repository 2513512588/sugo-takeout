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
 * 外卖商品分类表
 * @TableName takeout_goods_category
 */
@TableName(value ="takeout_goods_category")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutGoodsCategory implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("分类id")
    private Integer id;

    /**
     * 商品分类名称
     */
    @ApiModelProperty("商品分类名称")
    private String name;

    /**
     * 商家id
     */
    @JsonIgnore
    @ApiModelProperty("商家id")
    @TableField(select = false)
    private Integer sellerId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}