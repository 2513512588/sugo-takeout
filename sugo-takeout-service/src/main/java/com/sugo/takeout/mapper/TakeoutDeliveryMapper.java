package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutDelivery;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author hehaoyang
 * @Entity TakeoutDelivery
 */
public interface TakeoutDeliveryMapper extends BaseMapper<TakeoutDelivery> {

    @MapKey("seller_id")
    List<Map<Integer, Double>> getAvgDeliveryTimeBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    Double getAvgDeliveryTimeBySeller(@Param("sellerId") Integer sellerId);

    LocalDateTime getAcceptOrderTime(@Param("orderCode") String orderCode);

    TakeoutDelivery getLastDeliveryByOrderCode(@Param("orderCode") String orderCode, @Nullable @Param("riderId") Integer riderId, @Nullable @Param("sellerId") Integer sellerId);

}




