package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutAddress;


/**
 *
 */
public interface TakeoutAddressService extends IService<TakeoutAddress> {

    /**
     * 根据距离获取配送时间
     * @param distance 距离
     * @return 配送时间minute
     */
    long getDeliveryTime(long distance);

    /**
     * 根据距离获取配送费用
     * @param distance 距离
     * @return 配送费
     */
    double getDeliveryFee(long distance);

}
