package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.dto.TakeoutCouponDto;
import com.sugo.smart_city.bean.model.TakeoutCollection;
import com.sugo.smart_city.bean.model.TakeoutCoupon;
import com.sugo.smart_city.bean.model.TakeoutCouponReceive;
import com.sugo.smart_city.common.exception.SugoException;
import com.sugo.smart_city.mapper.TakeoutCouponMapper;
import com.sugo.smart_city.service.TakeoutCollectionService;
import com.sugo.smart_city.service.TakeoutCouponReceiveService;
import com.sugo.smart_city.service.TakeoutCouponService;
import com.sugo.smart_city.service.TakeoutOrderService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */
@Service
@AllArgsConstructor
public class TakeoutCouponServiceImpl extends ServiceImpl<TakeoutCouponMapper, TakeoutCoupon>
    implements TakeoutCouponService {

    private TakeoutCouponReceiveService takeoutCouponReceiveService;
    private TakeoutOrderService takeoutOrderService;
    private TakeoutCollectionService takeoutCollectionService;

    @Transactional(rollbackFor = SugoException.class)
    @Override
    public void receiveCoupon(Integer userId, TakeoutCoupon takeoutCoupon) {
        if (!StringUtils.isEmpty(takeoutCoupon.getQuantity()) && takeoutCoupon.getQuantity() <= 0){
            throw new SugoException("红包已经领完啦！");
        }
        LocalDateTime now = LocalDateTime.now();
        // 实际过期时间 < now 开始时间前过期了
        LocalDateTime expirationTime = takeoutCoupon.getExpirationTime();
        if (expirationTime != null && expirationTime.isBefore(now)){
            throw new SugoException("红包已过期");
        }
        // 实际开始时间 > now 还没到开始时间
        LocalDateTime startTime = takeoutCoupon.getStartTime();
        if (startTime != null && startTime.isAfter(now)){
            throw new SugoException("领取红包暂未开始");
        }
        Double conditionCostPrice = takeoutCoupon.getConditionCostPrice();
        if (conditionCostPrice != null && conditionCostPrice > 0){
            double totalPrice = takeoutOrderService.getTotalPriceByUserAndSeller(userId, takeoutCoupon.getSellerId());
            if (totalPrice < conditionCostPrice){
                throw new SugoException(String.format("消费金额不满%.2f元", conditionCostPrice));
            }
        }
        TakeoutCouponReceive build = TakeoutCouponReceive.builder()
                                                .couponId(takeoutCoupon.getId())
                                                .userId(userId)
                                                .expirationTime(now.plusSeconds(takeoutCoupon.getEffectiveDuration()))
                                                .build();
        try {
            boolean updateQuantity = true;
            boolean updateCollect = true;
            if (takeoutCoupon.getQuantity() != null){
                updateQuantity = updateById(TakeoutCoupon.builder().id(takeoutCoupon.getId()).quantity(takeoutCoupon.getQuantity() - 1).build());
            }
            boolean exists = takeoutCollectionService.exists(userId, takeoutCoupon.getSellerId());
            if (takeoutCoupon.getConditionIsFav() && !exists){
                updateCollect = takeoutCollectionService.save(TakeoutCollection.builder().userId(userId).sellerId(takeoutCoupon.getSellerId()).build());
            }
            boolean save = takeoutCouponReceiveService.save(build);
            if (!updateQuantity || !save || !updateCollect){
                throw new SugoException("领卷失败！");
            }
        }catch (DuplicateKeyException e){
            throw new SugoException("你已经领取过红包啦！");
        }
    }

    @Override
    public List<TakeoutCouponDto> list(Integer sellerId, Integer userId) {
        return baseMapper.findBySellerAndUser(sellerId, userId);
    }
}




