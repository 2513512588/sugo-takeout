package com.sugo.takeout.bean.dto;

import com.sugo.takeout.bean.model.TakeoutDelivery;
import com.sugo.takeout.bean.vo.OrderGoodsItemVo;
import com.sugo.takeout.bean.vo.SellerOrderDeliveryVo;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * @author hehaoyang
 */
@Data
public class SellerOrderDto {

    /**
     * 订单编号
     */
    private String code;
    /**
     * 预计送达时间
     */
    private LocalTime arriveTime;
    /**
     * 收货人姓名
     */
    private String consignee;
    /**
     * 收货人手机号
     */
    private String consigneePhone;
    /**
     * 收货地址
     */
    private String consigneeAddr;
    /**
     * 订单商品集合
     */
    private List<OrderGoodsItemVo> goodsItemList;

    /**
     * 配送信息状态
     */
    private List<SellerOrderDeliveryVo> deliveryInfoList;


}
