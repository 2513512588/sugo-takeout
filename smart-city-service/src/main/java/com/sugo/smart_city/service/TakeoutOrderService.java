package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutOrder;

/**
 *
 */
public interface TakeoutOrderService extends IService<TakeoutOrder> {

    double getAvgCostBySeller(Integer sellerId);

    Integer getAvgMonthSoldNumBySeller(Integer sellerId);
}
