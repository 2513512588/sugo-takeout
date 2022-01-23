package com.sugo.smart_city.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.smart_city.bean.model.TakeoutAddress;
import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.sugo.smart_city.bean.model.TakeoutOrder;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.bean.param.TakeoutOrderParam;

import java.util.List;

/**
 *
 */
public interface TakeoutOrderService extends IService<TakeoutOrder> {

    double getAvgCostBySeller(Integer sellerId);

    List<Double> getAvgCostBySellerList(List<Integer> sellerIds);

    Integer getAvgMonthOrderNumBySeller(Integer sellerId);

    List<Integer> getAvgMonthOrderNumBySellerList(List<Integer> sellerIds);

    double getTotalPriceByUserAndSeller(Integer userId, Integer sellerId);

    /**
     * @param takeoutCoupon 优惠卷
     * @param takeoutAddress 收货地址
     * @param takeoutSeller 商家信息
     * @param takeoutOrderParam 订单信息
     * @param userId 用户id
     * @return 订单号
     */
    String createOrder(TakeoutCoupon takeoutCoupon, TakeoutAddress takeoutAddress, TakeoutSeller takeoutSeller, TakeoutOrderParam takeoutOrderParam, Integer userId);
}
