package com.sugo.takeout.service;

import com.sugo.takeout.bean.dto.CouponDto;
import com.sugo.takeout.bean.model.TakeoutCoupon;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface TakeoutCouponService extends IService<TakeoutCoupon> {

    /**
     * 领取外卖红包
     * @param userId 用户id
     * @param takeoutCoupon 优惠卷对象
     */
    void receiveCoupon(Integer userId, TakeoutCoupon takeoutCoupon);

    List<CouponDto> list(Integer sellerId, Integer userId);

    TakeoutCoupon getByIdAndUser(Integer couponId, Integer userId);

    boolean useCoupon(Integer userId, Integer couponId);
}
