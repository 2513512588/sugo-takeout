package com.sugo.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sugo.takeout.bean.model.TakeoutDelivery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author hehaoyang
 * @Entity TakeoutDelivery
 */
public interface TakeoutDeliveryMapper extends BaseMapper<TakeoutDelivery> {

    List<Long> getAvgDeliveryTimeBySellerList(@Param("sellerIds") List<Integer> sellerIds);

    Long getAvgDeliveryTimeBySeller(@Param("sellerId") Integer sellerId);
}




