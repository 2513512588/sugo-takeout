package com.sugo.smart_city.mapper;

import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @Entity com.sugo.smart_city.bean.model.domain.TakeoutCoupon
 */
public interface TakeoutCouponMapper extends BaseMapper<TakeoutCoupon> {

    List<TakeoutCoupon> findBySellerId(Integer id);

}




