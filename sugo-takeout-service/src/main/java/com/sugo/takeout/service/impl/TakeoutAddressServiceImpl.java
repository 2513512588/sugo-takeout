package com.sugo.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.model.TakeoutAddress;
import com.sugo.takeout.mapper.TakeoutAddressMapper;
import com.sugo.takeout.service.TakeoutAddressService;
import org.springframework.stereotype.Service;


/**
 *
 */
@Service
public class TakeoutAddressServiceImpl extends ServiceImpl<TakeoutAddressMapper, TakeoutAddress>
    implements TakeoutAddressService{


    @Override
    public long getDeliveryTime(long distance) {
        return new Double(Math.min(Math.max(distance / 1000 * 1.5, 15), 45)).longValue();
    }

    @Override
    public double getDeliveryFee(long distance) {
        //todo 计算公式
        //todo 配送价格表 每公里x，最低，最高
        return Math.min(Math.max(distance / 1000 * 2, 2), 5);
    }
}




