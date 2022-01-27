package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sugo.takeout.bean.dto.TakeoutOrderDetailDto;
import com.sugo.takeout.bean.dto.TakeoutOrderListDto;
import com.sugo.takeout.bean.model.TakeoutOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.takeout.smartcity.entity.TakeoutOrder
 */
public interface TakeoutOrderMapper extends BaseMapper<TakeoutOrder> {

    double getAvgCostBySeller(@Param("sellerId") Integer sellerId);

    List<Double> getAvgCostBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    int getAvgMonthOrderNumBySeller(@Param("sellerId") Integer sellerId);

    List<Integer> getAvgMonthOrderNumBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    double getTotalPriceByUserAndSeller(@Param("userId") Integer userId, @Param("sellerId") Integer sellerId);

    IPage<TakeoutOrderListDto> list(IPage<TakeoutOrder> page, @Param("userId") Integer userId, @Param("statuses") Integer[] statuses);

    TakeoutOrderDetailDto getDetail(@Param("userId") Integer userId, @Param("orderCode") String orderCode);

}




