package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sugo.takeout.bean.dto.*;
import com.sugo.takeout.bean.model.TakeoutOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @Entity com.sugo.takeout.model.TakeoutOrder
 */
public interface TakeoutOrderMapper extends BaseMapper<TakeoutOrder> {

    double getAvgCostBySeller(@Param("sellerId") Integer sellerId);

    List<Double> getAvgCostBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    int getAvgMonthOrderNumBySeller(@Param("sellerId") Integer sellerId);

    List<Integer> getAvgMonthOrderNumBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    double getTotalPriceByUserAndSeller(@Param("userId") Integer userId, @Param("sellerId") Integer sellerId);

    IPage<OrderListDto> list(Page<TakeoutOrder> page, @Param("userId") Integer userId, @Param("statuses") Integer[] statuses);

    OrderDetailDto getDetail(@Param("userId") Integer userId, @Param("orderCode") String orderCode);

    int receiveOrder(@Param("riderId") Integer riderId, @Param("orderCode") String orderCode);

    IPage<String> getAllRiderOrderCodeList(Page<?> page);

    IPage<AcceptedRiderOrderDto> getAcceptedRiderOrderList(Page<?> page, @Param("riderId") Integer riderId, @Param("status") @Nullable Integer status);

    IPage<SellerOrderDto> getSellerOrderList(Page<TakeoutOrder> page, @Param("sellerId") Integer sellerId, @Param("status") Integer status);

    int updateOrderStatus(@Param("code") String code, @Param("status") int status);

    RiderOrderDataDto getTotalData(@Param("riderId") Integer riderId);
}




