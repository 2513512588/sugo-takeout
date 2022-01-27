package com.sugo.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.model.TakeoutDelivery;

import com.sugo.takeout.mapper.TakeoutDeliveryMapper;
import com.sugo.takeout.service.TakeoutDeliveryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TakeoutDeliveryServiceImpl extends ServiceImpl<TakeoutDeliveryMapper, TakeoutDelivery>
    implements TakeoutDeliveryService {

    @Override
    public Long getAvgDeliveryTimeBySeller(Integer sellerId) {
        return baseMapper.getAvgDeliveryTimeBySeller(sellerId);
    }

    @Override
    public List<Long> getAvgDeliveryTimeBySellerList(List<Integer> sellerIds) {
        return baseMapper.getAvgDeliveryTimeBySellerList(sellerIds);
    }
}




