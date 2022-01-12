package com.sugo.smart_city.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.smart_city.bean.model.TakeoutDelivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hehaoyang
 * @Entity com.sugo.smart_city.bean.model.TakeoutDelivery
 */
public interface TakeoutDeliveryMapper extends BaseMapper<TakeoutDelivery> {

    List<Long> getAvgDeliveryTimeBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    Long getAvgDeliveryTimeBySeller(@Param("sellerId") Integer sellerId);
}




