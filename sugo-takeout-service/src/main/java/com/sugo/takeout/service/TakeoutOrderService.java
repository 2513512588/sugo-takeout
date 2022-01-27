package com.sugo.takeout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.dto.TakeoutOrderDetailDto;
import com.sugo.takeout.bean.dto.TakeoutOrderListDto;
import com.sugo.takeout.bean.model.TakeoutAddress;
import com.sugo.takeout.bean.model.TakeoutCoupon;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.bean.param.TakeoutOrderParam;

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

    /**
     * @param page 分页信息
     * @param userId 用户id
     * @param statuses 订单状态列表
     * @return 订单列表
     */
    IPage<TakeoutOrderListDto> getList(IPage<TakeoutOrder> page, Integer userId, Integer[] statuses);

    /**
     * 根据编码
     * @param userId 用户id
     * @param orderCode 订单编号
     * @return 订单详情信息
     */
    TakeoutOrderDetailDto getDetail(Integer userId, String orderCode);
}
