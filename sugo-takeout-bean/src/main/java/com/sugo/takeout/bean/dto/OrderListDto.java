package com.sugo.takeout.bean.dto;

import com.sugo.takeout.bean.model.TakeoutDelivery;
import com.sugo.takeout.bean.vo.BasketGoodsItemVo;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class OrderListDto {

    /**
     * 订单编号
     */
    private String code;

    /**
     * 订单项
     */
    private List<BasketGoodsItemVo> orderItemList;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 店铺名称
     */
    private String sellerName;

    /**
     * 店铺id
     */
    private Integer sellerId;

    /**
     * 开始配送时间
     */
    private LocalTime deliveryTimeStart;

    /**
     * 结束配送时间
     */
    private LocalTime deliveryTimeEnd;

    /**
     * 订单总金额
     */
    private Double total;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 当前物流状态
     */
    private TakeoutDelivery delivery;

}
