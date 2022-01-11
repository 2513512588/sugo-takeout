package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutOrder;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.smart_city.smartcity.entity.TakeoutOrder
 */
public interface TakeoutOrderMapper extends BaseMapper<TakeoutOrder> {

    double getAvgCostBySeller(@Param("sellerId") Integer sellerId);

    int getAvgMonthSoldNumBySeller(@Param("sellerId") Integer sellerId);
}




