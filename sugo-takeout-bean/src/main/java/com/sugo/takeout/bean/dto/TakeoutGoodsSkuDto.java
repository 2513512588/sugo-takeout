package com.sugo.takeout.bean.dto;


import com.sugo.takeout.bean.model.TakeoutGoodsSku;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 商品Sku数据模型
 */
@Data
public class TakeoutGoodsSkuDto implements Serializable{

    /**
     * 库存类型
     */
    private String type;

    /**
     * 该type分类下的sku
     */
    private List<TakeoutGoodsSku> children;


}