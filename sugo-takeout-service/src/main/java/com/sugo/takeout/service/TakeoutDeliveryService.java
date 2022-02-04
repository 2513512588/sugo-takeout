package com.sugo.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.model.TakeoutDelivery;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface TakeoutDeliveryService extends IService<TakeoutDelivery> {

    /**
     * 获取订单最后的物流状态
     * @param orderCode 订单id
     * @param riderId 骑手id
     * @return 订单物流信息
     */
    TakeoutDelivery getLastDeliveryByOrderCodeAndRiderId(String orderCode, Integer riderId);

    /**
     * 获取订单最后的物流状态
     * @param orderCode 订单id
     * @param sellerId 店铺id
     * @return 订单物流信息
     */
    TakeoutDelivery getLastDeliveryByOrderCodeAndSellerId(String orderCode, Integer sellerId);

    /**
     * 获取订单最后的物流状态
     * @param orderCode 订单id
     * @return 订单物流信息
     */
    TakeoutDelivery getLastDeliveryByOrderCode(String orderCode);

    Double getAvgDeliveryTimeBySeller(Integer sellerId);

    /**
     * @param sellerIds 店铺id集合
     * @return <SellerId, AvgDeliveryTime>
     */
    Map<Integer, Double> getAvgDeliveryTimeBySellerList(List<Integer> sellerIds);

    /**
     * 商家出餐
     * @param orderCode 订单编号
     * @param sellerId 商家id
     * @return 是否出餐成功
     */
    boolean eatOut(String orderCode, Integer sellerId);


}
