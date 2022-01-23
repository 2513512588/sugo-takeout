package com.sugo.smart_city.mapper;

import com.sugo.smart_city.bean.dto.TakeoutCouponDto;
import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.sugo.smart_city.bean.model.domain.TakeoutCoupon
 */
public interface TakeoutCouponMapper extends BaseMapper<TakeoutCoupon> {

    List<TakeoutCoupon> findBySellerId(Integer id);

    List<TakeoutCouponDto> findBySellerAndUser(@Param("sellerId") Integer sellerId, @Param("userId") Integer userId);

    TakeoutCoupon getByIdAndUser(@Param("couponId") Integer couponId, @Param("userId") Integer userId);
}




