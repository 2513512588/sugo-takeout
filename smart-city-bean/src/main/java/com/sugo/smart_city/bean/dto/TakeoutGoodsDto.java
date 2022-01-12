package com.sugo.smart_city.bean.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class TakeoutGoodsDto implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

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
     * 商铺id
     */
    private Integer sellerId;

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


    private TakeoutSellerAdditionalDto additionalData = new TakeoutSellerAdditionalDto();

    @JsonIgnore
    private String location;
}