package com.sugo.takeout.mapper;

import com.sugo.takeout.bean.dto.TakeoutCouponDto;
import com.sugo.takeout.bean.model.TakeoutCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.sugo.takeout.bean.model.domain.TakeoutCoupon
 */
public interface TakeoutCouponMapper extends BaseMapper<TakeoutCoupon> {

    List<TakeoutCoupon> findBySellerId(Integer id);

    List<TakeoutCouponDto> findBySellerAndUser(@Param("sellerId") Integer sellerId, @Param("userId") Integer userId);

    TakeoutCoupon getByIdAndUser(@Param("couponId") Integer couponId, @Param("userId") Integer userId);

    int useCoupon(@Param("userId") Integer userId, @Param("couponId") Integer couponId);
}




