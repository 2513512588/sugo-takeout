package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.model.TakeoutDelivery;

import com.sugo.smart_city.mapper.TakeoutDeliveryMapper;
import com.sugo.smart_city.service.TakeoutDeliveryService;
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




