package com.sugo.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.enums.DeliveryStatus;
import com.sugo.takeout.bean.model.TakeoutDelivery;

import com.sugo.takeout.common.exception.SugoException;
import com.sugo.takeout.mapper.TakeoutDeliveryMapper;
import com.sugo.takeout.service.TakeoutDeliveryService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class TakeoutDeliveryServiceImpl extends ServiceImpl<TakeoutDeliveryMapper, TakeoutDelivery>
    implements TakeoutDeliveryService {

    @Override
    public TakeoutDelivery getUpdateDeliveryObjByOrderCodeAndRiderId(String orderCode, Integer riderId) {
        TakeoutDelivery takeoutDelivery = baseMapper.getUpdateDeliveryObj(orderCode, riderId, null);
        if (takeoutDelivery == null){
            throw new SugoException("订单物流信息不存在!");
        }
        return takeoutDelivery;
    }

    @Override
    public TakeoutDelivery getUpdateDeliveryObjByOrderCodeAndSellerId(String orderCode, Integer sellerId) {
        TakeoutDelivery takeoutDelivery = baseMapper.getUpdateDeliveryObj(orderCode, null, sellerId);
        if (takeoutDelivery == null){
            throw new SugoException("订单物流信息不存在!");
        }
        return takeoutDelivery;
    }

    @Override
    public TakeoutDelivery getLastDeliveryByOrderCodeAndUserId(String orderCode, Integer userId) {
        TakeoutDelivery takeoutDelivery = baseMapper.getLastDeliveryByOrderCodeAndUserId(orderCode, userId);
        if (takeoutDelivery == null){
            throw new SugoException("订单物流信息不存在!");
        }
        return takeoutDelivery;
    }

    @Override
    public TakeoutDelivery getUpdateDeliveryObjByOrderCode(String orderCode) {
        return baseMapper.getUpdateDeliveryObj(orderCode, null, null);
    }

    @Override
    public Double getAvgDeliveryTimeBySeller(Integer sellerId) {
        return baseMapper.getAvgDeliveryTimeBySeller(sellerId);
    }

    @Override
    public Map<Integer, Double> getAvgDeliveryTimeBySellerList(List<Integer> sellerIds) {
        List<Map<Integer, Double>> list = baseMapper.getAvgDeliveryTimeBySellerList(sellerIds);
        Map<Integer, Double> map = new HashMap<>(list.size());
        for (Map<Integer, Double> integerDoubleMap : list) {
            integerDoubleMap.putAll(map);
        }
        return map;
    }

    @Override
    public boolean eatOut(String orderCode, Integer sellerId) {
        try {
            TakeoutDelivery takeoutDelivery = getUpdateDeliveryObjByOrderCodeAndSellerId(orderCode, sellerId);
            takeoutDelivery.setSellerStatus(DeliveryStatus.MEALS_HAVE_BEEN_SERVED.getStatus());
            return baseMapper.insert(takeoutDelivery) == 1;
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            throw new SugoException("你已出过餐了！");
        }
    }


}




