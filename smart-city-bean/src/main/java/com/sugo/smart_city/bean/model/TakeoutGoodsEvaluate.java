package com.sugo.smart_city.bean.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 外卖商品评价表
 * @TableName takeout_goods_evaluate
 */
@TableName(value ="takeout_goods_evaluate")
@Data
public class TakeoutGoodsEvaluate implements Serializable {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderItemId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 店铺id
     */
    private Integer sellerId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 商品评价图片数组json
     */
    private Object images;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 评分
     */
    private BigDecimal score;

    /**
     * 评价时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 是否删除
     */
    private Boolean isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}