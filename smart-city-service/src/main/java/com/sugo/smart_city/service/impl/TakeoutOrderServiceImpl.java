package com.sugo.smart_city.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.model.TakeoutOrder;
import com.sugo.smart_city.mapper.TakeoutOrderMapper;
import com.sugo.smart_city.service.TakeoutOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TakeoutOrderServiceImpl extends ServiceImpl<TakeoutOrderMapper, TakeoutOrder>
    implements TakeoutOrderService{

    @Override
    public double getAvgCostBySeller(Integer sellerId) {
        return baseMapper.getAvgCostBySeller(sellerId);
    }

    @Override
    public Integer getAvgMonthSoldNumBySeller(Integer sellerId) {
        return baseMapper.getAvgMonthSoldNumBySeller(sellerId);
    }

    @Override
    public List<Double> getAvgCostBySellerList(List<Integer> sellerIds) {
        return baseMapper.getAvgCostBySellerList(sellerIds);
    }

    @Override
    public List<Integer> getAvgMonthSoldNumBySellerList(List<Integer> sellerIds) {
        return baseMapper.getAvgMonthSoldNumBySellerList(sellerIds);
    }
}




