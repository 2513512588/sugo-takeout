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
     * 获取更新物流状态对象
     * @param orderCode 订单id
     * @param riderId 骑手id
     * @return 订单物流信息
     */
    TakeoutDelivery getUpdateDeliveryObjByOrderCodeAndRiderId(String orderCode, Integer riderId);
    /**
     * 获取更新物流状态对象
     * @param orderCode 订单id
     * @param sellerId 店铺id
     * @return 订单物流信息
     */
    TakeoutDelivery getUpdateDeliveryObjByOrderCodeAndSellerId(String orderCode, Integer sellerId);


    /**
     * 获取更新物流状态对象
     * @param orderCode 订单id
     * @return 订单物流信息
     */
    TakeoutDelivery getUpdateDeliveryObjByOrderCode(String orderCode);

    /**
     * 获取订单最后的物流状态
     * @param orderCode 订单id
     * @param userId 用户id
     * @return 订单物流信息
     */
    TakeoutDelivery getLastDeliveryByOrderCodeAndUserId(String orderCode, Integer userId);


    /**
     * 获取平均配送时间
     * @param sellerId 商家id
     * @return 平均配送时间
     */
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
