package com.sugo.smart_city.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TakeoutBasketDto implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品信息
     */
    private TakeoutGoodsBasketDto goods;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}