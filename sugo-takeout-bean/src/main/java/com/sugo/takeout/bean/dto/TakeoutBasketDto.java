package com.sugo.takeout.bean.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sugo.takeout.bean.vo.TakeoutBasketGoodsItemVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *  购物车数据展示
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutBasketDto implements Serializable {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 商品信息
     */
    private TakeoutBasketGoodsItemVo goods;

    /**
     * 商品id
     */
    @JsonIgnore
    private Integer goodsId;

    /**
     * 加购数量
     */
    private Integer quantity;

    /**
     * 商品规格是否失效
     */
    private Boolean skuValid;

    /**
     * skuId组 json字符串
     */
    private String skuIdGroup;

    /**
     * 选中的sku名字集合
     */
    private List<String> skuNameList = new ArrayList<>();

}