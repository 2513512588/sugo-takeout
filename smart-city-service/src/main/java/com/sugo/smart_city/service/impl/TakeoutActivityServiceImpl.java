package com.sugo.smart_city.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.smart_city.bean.enums.TakeoutActivityType;
import com.sugo.smart_city.bean.model.TakeoutActivity;
import com.sugo.smart_city.mapper.TakeoutActivityMapper;
import com.sugo.smart_city.service.TakeoutActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author hehaoyang
 */
@Service
public class TakeoutActivityServiceImpl extends ServiceImpl<TakeoutActivityMapper, TakeoutActivity>
    implements TakeoutActivityService {

    @Override
    public List<TakeoutActivity> list(Integer sellerId) {
        return baseMapper.findBySellerId(sellerId);
    }

    @Override
    public double reduction(Integer sellerId, double total) {
        List<TakeoutActivity> activities = list(sellerId);
        Optional<Double> activityPrice = activities.stream().filter(item -> total >= item.getCondition()).collect(Collectors.toList()).stream().map(item -> {
            if (item.getType() == TakeoutActivityType.REDUCTION.getType()) {
                return total - item.getReduce();
            } else if (item.getType() == TakeoutActivityType.DISCOUNT.getType()) {
                return total * item.getReduce();
            }
            return total;
        }).sorted().findFirst();
        return activityPrice.orElse(total);
    }
}




