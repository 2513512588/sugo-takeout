package com.sugo.smart_city.bean.param;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 外卖商品表
 * @TableName takeout_goods
 */
@TableName(value ="takeout_goods")
@Data
public class TakeoutGoodsParam implements Serializable {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品分类id
     */
    private Integer categoryId;

    /**
     * 商品图片
     */
    private String cover;

    /**
     * 商品评分
     */
    private BigDecimal score;

    /**
     * 商品销量
     */
    private Integer salesVolume;

    /**
     * 商品价格
     */
    private BigDecimal price;

}