package com.sugo.takeout.bean.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺内商品数据模型 根据店铺和商品分类进行查询 过滤下架的商品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeoutGoodsDetailDto implements Serializable {
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
     * 商品图片
     */
    private String cover;

    /**
     * 商品评分
     */
    private Double score;

    /**
     * 商品销量
     */
    private Integer salesVolume;

    /**
     * 商品价格
     */
    private Double price;


    /**
     * 是否为必点品 0 不是 1是
     */
    private Boolean isMandatory;

    /**
     * 起购数量
     */
    private Integer minPurchaseNum;

    /**
     * 每份打包费
     */
    private Double packingFee;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 商品原价
     */
    private Double originPrice;

    /**
     * 限购数量
     */
    private Integer maxPurchaseNum;

    /**
     * 商品sku
     */
    private List<TakeoutGoodsSkuDto> skus;

}