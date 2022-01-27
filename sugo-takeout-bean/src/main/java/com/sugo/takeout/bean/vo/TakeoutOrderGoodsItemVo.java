package com.sugo.takeout.bean.vo;

import lombok.Data;

@Data
public class TakeoutOrderGoodsItemVo {

    /**
     * 商品图片
     */
    private String cover;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品购买数量
     */
    private Integer quantity;

    /**
     * sku json字符串
     */
    private String skuNameGroup;

    /**
     * 总计
     */
    private Double total;

}
