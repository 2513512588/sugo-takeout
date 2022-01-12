package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutOrder
 */
public interface TakeoutOrderMapper extends BaseMapper<TakeoutOrder> {

    double getAvgCostBySeller(@Param("sellerId") Integer sellerId);

    List<Double> getAvgCostBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    int getAvgMonthOrderNumBySeller(@Param("sellerId") Integer sellerId);

    List<Integer> getAvgMonthOrderNumBySellerList(@Param("sellerIds") List<Integer> sellerIds);
}




