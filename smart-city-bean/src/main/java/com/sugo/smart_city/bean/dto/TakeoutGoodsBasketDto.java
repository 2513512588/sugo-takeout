package com.sugo.smart_city.bean.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


/**
 * 购物车里的商品数据模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeoutGoodsBasketDto implements Serializable {
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
     * 商品价格
     */
    private Double price;

    /**
     * 商品原价
     */
    private Double originPrice;

    /**
     * 起购数量
     */
    private Integer minPurchaseNum;

    /**
     * 限购数量
     */
    private Integer maxPurchaseNum;

    /**
     * 每份打包费
     */
    private Double packingFee;

    /**
     * 库存数量
     */
    private Integer stock;

    /**
     * 是否上架 0 未上架 1已上架
     */
    private Integer status;

    /**
     * 是否为必点品 0 不是 1是
     */
    private Boolean isMandatory;

    /**
     * 商品sku
     */
    private List<TakeoutGoodsSkuDto> skus;

}