package com.sugo.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.model.TakeoutDelivery;

import java.util.List;

/**
 *
 */
public interface TakeoutDeliveryService extends IService<TakeoutDelivery> {

    Long getAvgDeliveryTimeBySeller(Integer sellerId);
    List<Long> getAvgDeliveryTimeBySellerList(List<Integer> sellerIds);

}
