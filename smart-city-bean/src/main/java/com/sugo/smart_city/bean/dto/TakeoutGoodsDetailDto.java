package com.sugo.smart_city.bean.dto;



import com.sugo.smart_city.bean.model.TakeoutGoodsSku;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 店铺内商品数据模型
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
    private BigDecimal score;

    /**
     * 商品销量
     */
    private Integer salesVolume;

    /**
     * 商品价格
     */
    private BigDecimal price;


    /**
     * 是否为必点品 0 不是 1是
     */
    private Boolean isMandatory;

    /**
     * 商品sku
     */
    private List<TakeoutGoodsSkuDto> skus;

}