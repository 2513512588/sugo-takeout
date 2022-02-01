package com.sugo.takeout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sugo.takeout.bean.dto.AcceptedRiderOrderDto;
import com.sugo.takeout.bean.dto.OrderDetailDto;
import com.sugo.takeout.bean.dto.OrderListDto;
import com.sugo.takeout.bean.dto.SellerOrderDto;
import com.sugo.takeout.bean.model.TakeoutAddress;
import com.sugo.takeout.bean.model.TakeoutCoupon;
import com.sugo.takeout.bean.model.TakeoutOrder;
import com.sugo.takeout.bean.model.TakeoutSeller;
import com.sugo.takeout.bean.param.OrderParam;
import org.springframework.lang.Nullable;

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
     * @param orderParam 订单信息
     * @param userId 用户id
     * @return 订单号
     */
    String createOrder(TakeoutCoupon takeoutCoupon, TakeoutAddress takeoutAddress, TakeoutSeller takeoutSeller, OrderParam orderParam, Integer userId);

    /**
     * @param page 分页信息
     * @param userId 用户id
     * @param statuses 订单状态列表
     * @return 订单列表
     */
    IPage<OrderListDto> getList(Page<TakeoutOrder> page, Integer userId, Integer[] statuses);

    /**
     * 根据编码
     * @param userId 用户id
     * @param orderCode 订单编号
     * @return 订单详情信息
     */
    OrderDetailDto getDetail(Integer userId, String orderCode);

    /**
     * 骑手接受订单
     * @param riderId 骑手id
     * @param orderCode 订单编号
     */
    void receiveOrder(Integer riderId, String orderCode);


    /**
     * 获取骑手订单编号
     * @param page 分页信息
     * @return 订单编号集合
     */
    IPage<String> getRiderOrderCodeList(Page<?> page);


    /**
     * 查询骑手已接受的订单
     * @param page 分页信息
     * @param riderId 骑手id
     * @param status 订单状态 1 待取餐 2 配送中
     * @return 订单信息集合
     */
    IPage<AcceptedRiderOrderDto> getAcceptedRiderOrderList(Page<?> page, Integer riderId, @Nullable Integer status);

    /**
     * 查询商品订单数据
     * @param page 分页信息
     * @param sellerId 商家id
     * @param status 订单状态
     * @return 商家订单列表
     */
    IPage<SellerOrderDto> getSellerOrderList(Page<TakeoutOrder> page, Integer sellerId, Integer status);
}
